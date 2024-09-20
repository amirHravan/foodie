package com.ravan.foodie.reserveinfo.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationMealInfo

data class MealRowUIModel(
    val mealName: String,
    val selfName: String,
    val foodName: String,
    val forgetCode: String? = null,
    val consumed: Boolean = false,
)

fun ReservationMealInfo.toMealRowUIModel(): MealRowUIModel {
    return MealRowUIModel(
        mealName = mealText,
        selfName = selfText,
        foodName = foodText,
        consumed = consumed,
    )
}
