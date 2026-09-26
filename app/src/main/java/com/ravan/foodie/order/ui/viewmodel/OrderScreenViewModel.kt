package com.ravan.foodie.order.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.credit.domain.usecase.GetRedirectLoginAsTokenUseCase
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.getNextSaturday
import com.ravan.foodie.domain.util.getPreviousSaturday
import com.ravan.foodie.order.domain.model.merge
import com.ravan.foodie.order.domain.usecase.GetAvailableSelfsUseCase
import com.ravan.foodie.order.domain.usecase.GetReservableProgramUseCase
import com.ravan.foodie.order.domain.usecase.ReserveFoodUseCase
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel
import com.ravan.foodie.order.ui.model.OrderFoodKey
import com.ravan.foodie.order.ui.model.OrderScreenUIModel
import com.ravan.foodie.order.ui.model.SelectSelfRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogUIModel
import com.ravan.foodie.order.ui.model.key
import com.ravan.foodie.order.ui.model.toReservableScreenUIModel
import com.ravan.foodie.order.ui.model.toSelfDialogUIModel
import com.ravan.foodie.order.ui.model.withFoodSelected
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch


class OrderScreenViewModel(
    private val getReservableProgramUseCase: GetReservableProgramUseCase,
    private val getAvailableSelfsUseCase: GetAvailableSelfsUseCase,
    private val reserveFoodUseCase: ReserveFoodUseCase,
    private val getRedirectLoginAsTokenUseCase: GetRedirectLoginAsTokenUseCase,
) : FoodieViewModel() {

    // reserve program
    val orderScreenUIModel =
        mutableStateOf<LoadableData<OrderScreenUIModel>>(LoadableData.NotLoaded)

    // foods with a reserve/cancel request in flight
    val pendingFoods = mutableStateOf<PersistentSet<OrderFoodKey>>(persistentSetOf())
    private var programRefreshJob: Job? = null

    // selected self row
    private var selectedSelfId = -1

    private var selectedSelfName: String = ""
    private var selfDialogUIModel: SelfDialogUIModel? = null
    val selectSelfRowUIModel = mutableStateOf(SelectSelfRowUIModel())

    // information box
    val showMessage = mutableStateOf(false)
    val informationBoxUIModel = mutableStateOf<FoodieInformationBoxUIModel?>(null)
    private var hideMessageJob: Job? = null

    // navigation
    val navBack: NavigationEvent = NavigationEvent()


    fun onLaunch() {
        if (selectedSelfId == -1) {
            onSelectSelfClick()
        } else {
            loadProgram()
        }
    }

    private fun loadProgram() {
        programRefreshJob?.cancel()
        programRefreshJob = viewModelScope.launch {
            getReservableScreenUIModel(
                offlineFirst = false,
                onSuccess = {
                    orderScreenUIModel.value = LoadableData.Loaded(it)
                },
            )
        }
    }

    fun onOrderFoodClick(detail: OrderFoodDetailUIModel) {
        val key = detail.key
        if (key in pendingFoods.value) return
        pendingFoods.value = pendingFoods.value.add(key)
        val selected = !detail.isSelected

        viewModelScope.launch {
            reserveFoodUseCase(
                foodTypeId = detail.foodTypeId,
                mealTypeId = detail.mealTypeId,
                programId = detail.programId,
                selected = selected,
            ).fold(
                onSuccess = { message ->
                    // Show the result right away; the refresh below corrects anything else the
                    // server changed (e.g. another food in the same meal).
                    (orderScreenUIModel.value as? LoadableData.Loaded)?.let {
                        orderScreenUIModel.value =
                            LoadableData.Loaded(it.data.withFoodSelected(key, selected))
                    }
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = message,
                        state = FoodieInformationBoxState.SUCCESS
                    )
                    refreshProgramSilently()
                },
                onFailure = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = it.message ?: "در رزرو غذا خطایی پیش آمده",
                        state = FoodieInformationBoxState.FAILED
                    )
                }
            )
            pendingFoods.value = pendingFoods.value.remove(key)
            showMessage()
        }
    }

    /**
     * Fetches the program again without showing the loading state. A newer call cancels an older
     * one, so a slow response can never overwrite a fresher one. If it replaces a visible load
     * (e.g. the self was just changed), it stays visible so the screen can't get stuck loading.
     */
    private fun refreshProgramSilently() {
        programRefreshJob?.cancel()
        programRefreshJob = viewModelScope.launch {
            delay(PROGRAM_REFRESH_DEBOUNCE_MS)
            getReservableScreenUIModel(
                offlineFirst = orderScreenUIModel.value is LoadableData.Loaded,
                onSuccess = {
                    orderScreenUIModel.value = LoadableData.Loaded(it)
                },
            )
        }
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onRefresh() {
        programRefreshJob?.cancel()
        programRefreshJob = viewModelScope.launch {
            getReservableScreenUIModel(
                offlineFirst = false,
                onSuccess = {
                    orderScreenUIModel.value = LoadableData.Loaded(it)
                },
            )
        }
    }

    fun onSelectSelfClick() {
        viewModelScope.launch {
            getAvailableSelfsUseCase().fold(
                onSuccess = {
                    selfDialogUIModel = it.toSelfDialogUIModel()
                    selectSelfRowUIModel.value = SelectSelfRowUIModel(
                        selectedSelfName,
                        selfDialogUIModel = it.toSelfDialogUIModel()
                    )
                },
                onFailure = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        state = FoodieInformationBoxState.FAILED,
                        message = it.message ?: "در گرفتن سلف‌های مجاز خطایی پیش آمده"
                    )
                    showMessage()
                }
            )

        }
    }

    fun onSelfClick(
        data: SelfDialogRowUIModel
    ) {

        selectedSelfName = data.name

        selectSelfRowUIModel.value = SelectSelfRowUIModel(
            selectedSelfName = selectedSelfName,
            selfDialogUIModel = selfDialogUIModel
        )
        selectedSelfId = data.id
        loadProgram()
    }

    private fun showMessage() {
        showMessage.value = true
        hideMessageJob?.cancel()
        hideMessageJob = viewModelScope.launch {
            delay(MESSAGE_DURATION_MS)
            showMessage.value = false
        }
    }

    private suspend fun getReservableScreenUIModel(
        offlineFirst: Boolean = false,
        onSuccess: (OrderScreenUIModel) -> Unit = {},
    ) {
        if (selectedSelfId == -1) return
        if (!offlineFirst) {
            orderScreenUIModel.value = LoadableData.Loading
        }
        val previousProgram = getReservableProgramUseCase(
            selfId = selectedSelfId,
            weekStartDate = getPreviousSaturday()
        )
        val nextProgram = getReservableProgramUseCase(
            selfId = selectedSelfId,
            weekStartDate = getNextSaturday()
        )

        // The repository turns every exception into a failed Result, cancellation included, so
        // check here that this load hasn't been replaced by a newer one before touching state.
        currentCoroutineContext().ensureActive()

        if (previousProgram.isFailure && nextProgram.isFailure) {
            // A silent refresh keeps what is on screen; a visible load shows the retry card
            // instead of spinning forever.
            if (!offlineFirst) {
                orderScreenUIModel.value = LoadableData.Failed(
                    nextProgram.exceptionOrNull()?.message
                        ?: "هیچ برنامه غذایی‌ای برای این سلف تعریف نشده است."
                )
            }
        } else {
            return onSuccess(
                previousProgram.getOrNull().merge(nextProgram.getOrNull())
                    .toReservableScreenUIModel()
            )
        }
    }

    fun onIncreaseCreditClick(
        invokeIntent: (String) -> Unit,
    ) {
        viewModelScope.launch {

            getRedirectLoginAsTokenUseCase().fold(
                onSuccess = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = "در حال انتقال به صفحه افزایش اعتبار...",
                        state = FoodieInformationBoxState.SUCCESS
                    )

                    val url = Uri.Builder()
                        .scheme("https")  // Protocol (https, http, etc.)
                        .authority("setad.dining.sharif.edu")  // Base URL
                        .appendPath("j_security_check")  // Path segment (if any)
                        .appendQueryParameter("loginAsToken", it.loginAsToken)  // Add query params
                        .appendQueryParameter("redirect", "/nurture/user/credit/charge/view.rose")
                        .build()
                        .toString()

                    invokeIntent(url)
                },
                onFailure = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = it.message ?: "در افزایش اعتبار خطایی پیش آمده",
                        state = FoodieInformationBoxState.FAILED
                    )
                }
            )
            showMessage()
        }

    }

    private companion object {
        const val PROGRAM_REFRESH_DEBOUNCE_MS = 300L
        const val MESSAGE_DURATION_MS = 3000L
    }
}
