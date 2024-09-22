package com.ravan.foodie.forget.domain.usecase

import com.ravan.foodie.domain.util.getToday
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import com.ravan.foodie.reserveinfo.domain.usecase.GetReservationInformationUseCase

class GetTodayMealUseCase(
    // bad practice: adds coupling between use cases
    private val getReservationInformationUseCase: GetReservationInformationUseCase,
) {

    suspend operator fun invoke(token: String): Result<ReservationInfo> {
        return getReservationInformationUseCase(token).map {
            ReservationInfo(
                remainCredit = it.remainCredit,
                dayInfoList = it.dayInfoList.filter { reservationDayInfo -> reservationDayInfo.date == getToday() }
            )
        }
    }

}