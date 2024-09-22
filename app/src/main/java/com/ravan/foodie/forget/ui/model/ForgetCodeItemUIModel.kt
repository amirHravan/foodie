package com.ravan.foodie.forget.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationDayInfo

data class ForgetCodeItemUIModel(
    val foodDetails: List<ForgetCodeFoodDetailUIModel?>,
)

fun ReservationDayInfo.toForgetCodeItemUIModel(codeMap: Map<Int, String>): ForgetCodeItemUIModel {
    return ForgetCodeItemUIModel(
        foodDetails = this.mealInfo.map { it?.toForgetCodeFoodDetailUIModel(codeMap) },
    )
}
