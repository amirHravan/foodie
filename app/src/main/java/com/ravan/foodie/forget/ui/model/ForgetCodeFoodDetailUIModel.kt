package com.ravan.foodie.forget.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationMealInfo

data class ForgetCodeFoodDetailUIModel(
    val mealName: String,
    val selfName: String,
    val foodName: String,
    val reserveId: Int,
    val forgetCode: String? = null,
    val consumed: Boolean = false,
)

fun ReservationMealInfo.toForgetCodeFoodDetailUIModel(codeMap: Map<Int, String>): ForgetCodeFoodDetailUIModel {
    return ForgetCodeFoodDetailUIModel(
        selfName = this.selfText,
        foodName = this.foodText,
        reserveId = this.id,
        forgetCode = codeMap[id],
        consumed = this.consumed,
        mealName = this.mealText
    )
}