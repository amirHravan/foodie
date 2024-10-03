package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

data class ReservationInfoScreenUIModel(
    val reservationInfoCardUIModelList: List<ReservationInfoCardUIModel>,
)

fun ReservationInfo.toReservationInfoScreenUIModel(
    forgetCodeMap: Map<Int, String>
): ReservationInfoScreenUIModel {
    return ReservationInfoScreenUIModel(
        reservationInfoCardUIModelList = dayInfoList.map {
            it.toReservationInfoCardUIModel(
                forgetCodeMap
            )
        }
            .filter { it.mealRowUIModelList.isNotEmpty() }
    )
}
