package com.ravan.foodie.reserveinfo.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.getNextSaturday
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import com.ravan.foodie.reserveinfo.domain.usecase.GetReservationInformationUseCase
import com.ravan.foodie.reserveinfo.ui.model.toReservationInfoScreenUIModel
import kotlinx.coroutines.launch

class ReservationInfoViewModel(
    val getReservationInformationUseCase: GetReservationInformationUseCase,
) : FoodieViewModel() {

    val reservationInfo =
        mutableStateOf<LoadableData>(LoadableData.NotLoaded)
    val navBack = NavigationEvent()

    fun onLaunch() {
        viewModelScope.launch {
            reservationInfo.value = LoadableData.Loading
            var data: ReservationInfo? = null

            getReservationInformationUseCase().let { result ->
                result.onSuccess {
                    data = it
                }
            }
            getReservationInformationUseCase(
                weekStartDate = getNextSaturday()
            ).let { result ->
                result.onSuccess {
                    data = data?.plus(it) ?: it
                }
            }

            data?.let {
                reservationInfo.value = LoadableData.Loaded(it.toReservationInfoScreenUIModel())
            } ?: run {
                reservationInfo.value = LoadableData.Failed(
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

}