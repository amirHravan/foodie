package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ReservationInfoScreenUIModel(
    val reservationInfoCardUIModelList: ImmutableList<ReservationInfoCardUIModel>,
)

fun ReservationInfo.toReservationInfoScreenUIModel(
    forgetCodeMap: Map<Int, String>
): ReservationInfoScreenUIModel {
    return ReservationInfoScreenUIModel(
        reservationInfoCardUIModelList = dayInfoList
            .map {
                it.toReservationInfoCardUIModel(
                    forgetCodeMap
                )
            }
            .filter { it.mealRowUIModelList.isNotEmpty() }
            .toImmutableList()
    )
}
