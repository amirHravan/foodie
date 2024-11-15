package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.reserveinfo.domain.model.ReservationDayInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ReservationInfoCardUIModel(
    val farsiDayName: String,
    val farsiDate: String,
    val mealRowUIModelList: ImmutableList<MealRowUIModel>,
)

fun ReservationDayInfo.toReservationInfoCardUIModel(
    forgetCodeMap: Map<Int, String>
): ReservationInfoCardUIModel {
    return ReservationInfoCardUIModel(
        farsiDayName = name,
        farsiDate = iranianDate.toLocalNumber(),
        mealRowUIModelList = mealInfo.mapNotNull { it?.toMealRowUIModel(forgetCodeMap[it.id]) }
            .sortedBy { !it.consumed }.toImmutableList()
    )
}