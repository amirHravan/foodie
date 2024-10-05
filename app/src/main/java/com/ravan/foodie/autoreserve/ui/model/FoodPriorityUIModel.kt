package com.ravan.foodie.autoreserve.ui.model

import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority

data class FoodPriorityUIModel(
    val id: Int,
    val name: String,
    var priority: Int = 1
)

fun AutoReserveFoodPriority.toFoodPriorityUIModel(): FoodPriorityUIModel {
    return FoodPriorityUIModel(
        id = id,
        name = name,
        priority = priority
    )
}