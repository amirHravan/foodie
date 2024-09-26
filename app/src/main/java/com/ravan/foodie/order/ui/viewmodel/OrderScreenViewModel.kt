package com.ravan.foodie.order.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.getNextSaturday
import com.ravan.foodie.domain.util.getPreviousSaturday
import com.ravan.foodie.order.domain.model.merge
import com.ravan.foodie.order.domain.usecase.GetAvailableSelfs
import com.ravan.foodie.order.domain.usecase.GetReservableProgramUseCase
import com.ravan.foodie.order.domain.usecase.ReserveFoodUseCase
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel
import com.ravan.foodie.order.ui.model.OrderScreenUIModel
import com.ravan.foodie.order.ui.model.SelfRowUIModel
import com.ravan.foodie.order.ui.model.toReservableScreenUIModel
import com.ravan.foodie.order.ui.model.toSelfDialogUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class OrderScreenViewModel(
    private val getReservableProgramUseCase: GetReservableProgramUseCase,
    private val getAvailableSelfs: GetAvailableSelfs,
    private val reserveFoodUseCase: ReserveFoodUseCase,
) : FoodieViewModel() {

    // reserve program
    val orderScreenUIModel = mutableStateOf<LoadableData>(LoadableData.NotLoaded)

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

    private fun loadProgram() {
        viewModelScope.launch {
            getReservableScreenUIModel(
                offlineFirst = false,
                onSuccess = {
                    orderScreenUIModel.value = LoadableData.Loaded(it)
                },
            )
        }
    }

    fun onOrderFoodClick(
        detail: OrderFoodDetailUIModel,
        onFinish: () -> Unit,
    ) {
        viewModelScope.launch {
            reserveFoodUseCase(
                foodTypeId = detail.foodTypeId,
                mealTypeId = detail.mealTypeId,
                programId = detail.programId,
                selected = !detail.isSelected,
            ).fold(
                onSuccess = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = "عملیات رزرو/حذف با موفقت انجام شد.",
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
                        message = it.message ?: "در رزرو غذا خطایی پیش آمده",
                        state = FoodieInformationBoxState.FAILED
                    )
                }
            )
            showMessage()
            onFinish()
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
            )
        }
    }

    fun onSelectSelfClick() {
        viewModelScope.launch {
            getAvailableSelfs().fold(
                onSuccess = {
                    selfDialogUIModel.value = LoadableData.Loaded(it.toSelfDialogUIModel())
                    shouldShowSelfDialog.value = true
                },
                onFailure = {
                    selfDialogUIModel.value = LoadableData.Failed(
                        it.message ?: "در گرفتن سلف های مجاز خطایی پیش آمده",
                    )
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
        data: SelfRowUIModel
    ) {
        selectedSelf.value = data.name
        selectedSelfId = data.id
        shouldShowSelfDialog.value = false
        loadProgram()
    }

    private fun showMessage() {
        showMessage.value = true
        viewModelScope.launch {
            delay(3000)
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

        if (previousProgram.isFailure && nextProgram.isFailure) {
            informationBoxUIModel.value = FoodieInformationBoxUIModel(
                message = "هیچ برنامه غذایی‌ای برای این سلف تعریف نشده است.",
                state = FoodieInformationBoxState.FAILED
            )
        } else {
            Log.d("foods", "$previousProgram | $nextProgram")
            return onSuccess(
                previousProgram.getOrNull().merge(nextProgram.getOrNull())
                    .toReservableScreenUIModel()
            )


        }
    }

}