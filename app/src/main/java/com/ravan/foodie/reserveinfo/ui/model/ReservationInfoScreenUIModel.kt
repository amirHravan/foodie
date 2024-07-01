package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

data class ReservationInfoScreenUIModel(
    val reservationInfoCardUIModelList: List<ReservationInfoCardUIModel>,
)

fun ReservationInfo.toReservationInfoScreenUIModel(): ReservationInfoScreenUIModel {
    return ReservationInfoScreenUIModel(
        reservationInfoCardUIModelList = dayInfoList.map { it.toReservationInfoCardUIModel() }
            .filter { it.mealRowUIModelList.isNotEmpty() }
    )
}
