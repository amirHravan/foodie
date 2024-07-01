package com.ravan.foodie.order.ui.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.RavanViewModel
import com.ravan.foodie.domain.usecase.GetSavedSamadTokenUseCase
import com.ravan.foodie.domain.util.getNextSaturdayDate
import com.ravan.foodie.domain.util.getThisWeekSaturdayDate
import com.ravan.foodie.order.domain.usecase.GetAvailableSelfs
import com.ravan.foodie.order.domain.usecase.GetReservableProgramUseCase
import com.ravan.foodie.order.domain.usecase.ReserveFoodUseCase
import com.ravan.foodie.order.ui.model.OrderScreenUIModel
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel
import com.ravan.foodie.order.ui.model.SelfRowUIModel
import com.ravan.foodie.order.ui.model.toReservableScreenUIModel
import com.ravan.foodie.order.ui.model.toSelfDialogUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
class OrderScreenViewModel(
    private val getReservableProgramUseCase: GetReservableProgramUseCase,
    private val getSavedSamadTokenUseCase: GetSavedSamadTokenUseCase,
    private val getAvailableSelfs: GetAvailableSelfs,
    private val reserveFoodUseCase: ReserveFoodUseCase,
) : RavanViewModel() {

    // reserve program
    val orderScreenUIModel = mutableStateOf<LoadableData>(LoadableData.NotLoaded)
    val shouldLoadMore = mutableStateOf(true)

    // selected self row
    private var selectedSelfId = -1
    val selectedSelf = mutableStateOf("")
    val selfDialogUIModel = mutableStateOf<LoadableData>(LoadableData.NotLoaded)
    val shouldShowSelfDialog = mutableStateOf(false)

    // information box
    val showMessage = mutableStateOf(false)
    val informationBoxUIModel = mutableStateOf<FoodieInformationBoxUIModel?>(null)

    // navigation
    val navBack: NavigationEvent = NavigationEvent()


    fun onLaunch() {
        onSelectSelfClick()
    }

    private fun loadReservableScreen() {
        viewModelScope.launch {
            getReservableScreenUIModel(
                offlineFirst = false,
                onSuccess = {
                    orderScreenUIModel.value = LoadableData.Loaded(it)
                },
                onFailure = {
                    orderScreenUIModel.value =
                        LoadableData.Failed(it.message ?: "در دریافت لیست غذا مشکلی پیش آمده.")
                }
            )
        }
    }

    fun onOrderFoodClick(
        detail: OrderFoodDetailUIModel
    ) {
        viewModelScope.launch {
            getSavedSamadTokenUseCase()?.let { token ->
                reserveFoodUseCase(
                    foodTypeId = detail.foodTypeId,
                    mealTypeId = detail.mealTypeId,
                    programId = detail.programId,
                    selected = !detail.isSelected,
                    token = token,
                ).fold(
                    onSuccess = {
                        informationBoxUIModel.value = FoodieInformationBoxUIModel(
                            message = "رزرو شما با موفقیت انجام شد",
                            state = FoodieInformationBoxState.SUCCESS
                        )
                        getReservableScreenUIModel(
                            offlineFirst = true,
                            onSuccess = {
                                orderScreenUIModel.value = LoadableData.Loaded(it)
                            },
                        )
                    },
                    onFailure = {
                        informationBoxUIModel.value = FoodieInformationBoxUIModel(
                            message = if (it.message.isNullOrBlank()) "خطا در رزرو" else it.message!!,
                            state = FoodieInformationBoxState.FAILED
                        )
                    }
                )
                showMessage()
            }
        }
    }

    fun onLoadNextWeekProgram() {
        if (!shouldLoadMore.value) return

        viewModelScope.launch {
            getReservableScreenUIModel(
                weekStartDate = getNextSaturdayDate(),
                offlineFirst = true,
                onSuccess = { new ->
                    (orderScreenUIModel.value as LoadableData.Loaded<OrderScreenUIModel>).let { old ->
                        val currentList = old.data.orderCardUIModelList
                        val newList = currentList + new.orderCardUIModelList
                        orderScreenUIModel.value =
                            LoadableData.Loaded(
                                OrderScreenUIModel(
                                    orderCardUIModelList = newList
                                )
                            )
                    }
                }
            )
            shouldLoadMore.value = false
        }
    }

    fun onBackClick() {
        if (selectedSelf.value.isNotEmpty() && shouldShowSelfDialog.value) {
            shouldShowSelfDialog.value = false
        } else {
            navBack.navigate()
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            getReservableScreenUIModel(
                offlineFirst = false,
                onSuccess = {
                    orderScreenUIModel.value = LoadableData.Loaded(it)
                },
                onFailure = {
                    orderScreenUIModel.value =
                        LoadableData.Failed(it.message ?: "در دریافت لیست غذا مشکلی پیش آمده.")
                }
            )
        }
    }

    fun onSelectSelfClick() {
        viewModelScope.launch {
            getSavedSamadTokenUseCase()?.let { token ->
                getAvailableSelfs(token).fold(
                    onSuccess = {
                        selfDialogUIModel.value = LoadableData.Loaded(it.toSelfDialogUIModel())
                        shouldShowSelfDialog.value = true
                    },
                    onFailure = {
                        selfDialogUIModel.value = LoadableData.Failed(it.message ?: "در گرفتن سلف های مجاز خطایی پیش آمده",)
                        informationBoxUIModel.value = FoodieInformationBoxUIModel(
                            state = FoodieInformationBoxState.FAILED,
                            message = it.message ?: "در گرفتن سلف‌های مجاز خطایی پیش آمده"
                        )
                        showMessage()
                    }
                )
            }
        }
    }

    fun onSelfClick(
        data: SelfRowUIModel
    ) {
        selectedSelf.value = data.name
        selectedSelfId = data.id
        loadReservableScreen()
        shouldShowSelfDialog.value = false
    }

    private fun showMessage() {
        showMessage.value = true
        viewModelScope.launch {
            delay(3000)
            showMessage.value = false
        }
    }

    private suspend fun getReservableScreenUIModel(
        weekStartDate: String? = null,
        offlineFirst: Boolean = false,
        onSuccess: (OrderScreenUIModel) -> Unit = {},
        onFailure: (Throwable) -> Unit = {},
    ) {
        if (selectedSelfId == -1) return
        if (!offlineFirst) {
            orderScreenUIModel.value = LoadableData.Loading
        }
        val result = getSavedSamadTokenUseCase()?.let { token ->
            getReservableProgramUseCase(
                token = token,
                selfId = selectedSelfId,
                weekStartDate = weekStartDate ?: getThisWeekSaturdayDate()

            ).map { it.toReservableScreenUIModel() }
        }

        result?.fold(
            onSuccess = onSuccess,
            onFailure = onFailure,
        )
    }

}