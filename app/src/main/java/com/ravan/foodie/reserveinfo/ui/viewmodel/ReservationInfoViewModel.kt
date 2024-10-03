package com.ravan.foodie.reserveinfo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.getNextSaturday
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import com.ravan.foodie.reserveinfo.domain.usecase.GetForgetCodeMapCacheUseCase
import com.ravan.foodie.reserveinfo.domain.usecase.GetForgetCodeUseCase
import com.ravan.foodie.reserveinfo.domain.usecase.GetReservationInformationUseCase
import com.ravan.foodie.reserveinfo.ui.model.ReservationInfoScreenUIModel
import com.ravan.foodie.reserveinfo.ui.model.toReservationInfoScreenUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReservationInfoViewModel(
    private val getReservationInformationUseCase: GetReservationInformationUseCase,
    private val getForgetCodeUseCase: GetForgetCodeUseCase,
    private val getForgetCodeMapCache: GetForgetCodeMapCacheUseCase,
) : FoodieViewModel() {

    val reservationInfoUIModel =
        mutableStateOf<LoadableData<ReservationInfoScreenUIModel?>>(LoadableData.NotLoaded)
    private var reservationInfo: ReservationInfo? = null

    val informationBoxUIModel = mutableStateOf<FoodieInformationBoxUIModel?>(null)
    val showMessage = mutableStateOf(false)

    val navBack = NavigationEvent()

    fun onLaunch() {
        viewModelScope.launch {
            reservationInfoUIModel.value = LoadableData.Loading

            getReservationInformationUseCase().let { result ->
                result.onSuccess {
                    reservationInfo = it
                }
            }
            getReservationInformationUseCase(
                weekStartDate = getNextSaturday()
            ).let { result ->
                result.onSuccess {
                    reservationInfo = reservationInfo?.plus(it) ?: it
                }
            }

            reservationInfo?.let {
                reservationInfoUIModel.value =
                    LoadableData.Loaded(it.toReservationInfoScreenUIModel(forgetCodeMap = getForgetCodeMapCache()))
            } ?: run {
                reservationInfoUIModel.value = LoadableData.Failed(
                    "در دریافت برنامه غذایی مشکلی پیش آمده."
                )
            }
        }
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onRefresh() {
        onLaunch()
    }

    fun onGetForgetCode(
        reserveId: Int,
        onFinish: () -> Unit,
    ) {

        viewModelScope.launch {
            getForgetCodeUseCase(reserveId = reserveId).fold(
                onSuccess = { forgetCode ->
                    val forgetCodeMap = getForgetCodeMapCache()
                    Log.d("temp", forgetCodeMap.toString())
                    Log.d("temp", forgetCode.code)
                    reservationInfoUIModel.value = LoadableData.Loaded(
                        reservationInfo?.toReservationInfoScreenUIModel(forgetCodeMap)
                    )
                },
                onFailure = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        state = FoodieInformationBoxState.FAILED,
                        message = "در دریافت کد فراموشی غذای مورد نظر خطایی پیش اومده",
                    )

                    showMessage.value = true
                    viewModelScope.launch {
                        delay(3000)
                        showMessage.value = false
                    }
                }
            )
            onFinish()
        }

    }

}