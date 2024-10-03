package com.ravan.foodie.autoreserve.ui.model

data class FoodPriorityUIModel(
    val id: Int,
    val name: String,
    var priority: Int = 1
)