package com.ravan.foodie.reserveinfo.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.RavanViewModel
import com.ravan.foodie.domain.usecase.GetSavedSamadTokenUseCase
import com.ravan.foodie.reserveinfo.domain.usecase.GetReservationInformationUseCase
import com.ravan.foodie.reserveinfo.ui.model.toReservationInfoScreenUIModel
import kotlinx.coroutines.launch

class ReservationInfoViewModel(
    val getReservationInformationUseCase: GetReservationInformationUseCase,
    val getSavedSamadTokenUseCase: GetSavedSamadTokenUseCase,
) : RavanViewModel() {

    val reservationInfo =
        mutableStateOf<LoadableData>(LoadableData.NotLoaded)
    val navBack = NavigationEvent()

    fun onLaunch() {
        viewModelScope.launch {
            reservationInfo.value = LoadableData.Loading
            getSavedSamadTokenUseCase()?.let { token ->
                getReservationInformationUseCase(token).let { result ->
                    result.fold(
                        onSuccess = {
                            reservationInfo.value =
                                LoadableData.Loaded(it.toReservationInfoScreenUIModel())
                        },
                        onFailure = {
                            reservationInfo.value = LoadableData.Failed(
                                it.message ?: "در دریافت برنامه غذایی مشکلی پیش آمده."
                            )
                        }
                    )
                }
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