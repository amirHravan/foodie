package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationMealInfo

data class MealRowUIModel(
    val mealName: String,
    val selfName: String,
    val foodName: String,
    val reserveId: Int,
    val forgetCode: String? = null,
    val consumed: Boolean = false,
)

fun ReservationMealInfo.toMealRowUIModel(
    forgetCode: String? = null
): MealRowUIModel {
    return MealRowUIModel(
        mealName = mealText,
        selfName = selfText,
        foodName = foodText,
        consumed = consumed,
        reserveId = id,
        forgetCode = forgetCode
    )
}
