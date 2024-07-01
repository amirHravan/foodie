package com.ravan.foodie.domain.ui.model

enum class FoodieInformationBoxState {
    SUCCESS,
    FAILED
}

data class FoodieInformationBoxUIModel(
    val state: FoodieInformationBoxState,
    val message: String,
)

fun Boolean.toFoodieInformationBoxState() =
    if (this) FoodieInformationBoxState.SUCCESS else FoodieInformationBoxState.FAILED