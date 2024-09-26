package com.ravan.foodie.forget.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.forget.domain.usecase.GetForgetCodeUseCase
import com.ravan.foodie.forget.domain.usecase.GetTodayMealUseCase
import com.ravan.foodie.forget.ui.model.toForgetCodeScreenUIModel
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgetCodeViewModel(
    private val getForgetCodeUseCase: GetForgetCodeUseCase,
    private val getTodayMealUseCase: GetTodayMealUseCase,
) : FoodieViewModel() {

    val forgetCodeScreenUIModel = mutableStateOf<LoadableData>(LoadableData.NotLoaded)
    val navBack = NavigationEvent()
    val buttonEnables = mutableStateOf(true)
    private var todayMealData: ReservationInfo? = null
    private val forgetCodeMap = mutableMapOf<Int, String>()
    val informationBoxUIModel = mutableStateOf<FoodieInformationBoxUIModel?>(null)
    val showNotification = mutableStateOf(false)

    fun onLaunch() {
        loadForgetCodePage()
    }

    fun onRefresh() {
        onLaunch()
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onGetForgetCode(
        reserveId: Int,
    ) {
        buttonEnables.value = false

        viewModelScope.launch {
            getForgetCodeUseCase(reserveId = reserveId).fold(
                onSuccess = { forgetCode ->
                    forgetCodeMap[reserveId] = forgetCode.code
                    forgetCodeScreenUIModel.value = LoadableData.Loaded(
                        todayMealData?.toForgetCodeScreenUIModel(forgetCodeMap)
                    )
                },
                onFailure = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        state = FoodieInformationBoxState.FAILED,
                        message = "در دریافت کد فراموشی غذای مورد نظر خطایی پیش اومده",
                    )

                    showNotification.value = true
                    viewModelScope.launch {
                        delay(3000)
                        showNotification.value = false
                    }
                }
            )

            buttonEnables.value = true
        }

    }

    private fun loadForgetCodePage() {
        forgetCodeScreenUIModel.value = LoadableData.Loading
        viewModelScope.launch {
            getTodayMealUseCase().fold(
                onSuccess = {
                    todayMealData = it
                    forgetCodeScreenUIModel.value =
                        LoadableData.Loaded(it.toForgetCodeScreenUIModel(forgetCodeMap))
                },
                onFailure = {
                    forgetCodeScreenUIModel.value =
                        LoadableData.Failed("در دریافت اطلاعات صفحه مشکلی پیش آمده. زارت!")
                }
            )

        }
    }

}
