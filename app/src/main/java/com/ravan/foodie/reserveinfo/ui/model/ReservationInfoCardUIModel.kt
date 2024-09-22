package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.reserveinfo.domain.model.ReservationDayInfo

data class ReservationInfoCardUIModel(
    val farsiDayName: String,
    val farsiDate: String,
    val mealRowUIModelList: List<MealRowUIModel>,
)

fun ReservationDayInfo.toReservationInfoCardUIModel(): ReservationInfoCardUIModel {
    return ReservationInfoCardUIModel(
        farsiDayName = name,
        farsiDate = iranianDate.toLocalNumber(),
        mealRowUIModelList = mealInfo.mapNotNull { it?.toMealRowUIModel() }
            .sortedBy { !it.consumed }
    )
}