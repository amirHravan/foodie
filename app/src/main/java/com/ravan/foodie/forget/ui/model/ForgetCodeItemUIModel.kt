package com.ravan.foodie.forget.ui.model

import com.ravan.foodie.order.domain.model.MealType

data class ForgetCodeItemUIModel(
    val mealType: MealType,
    val foodDetails: List<ForgetCodeFoodDetailUIModel>,
    val consumed: Boolean = false,

)
