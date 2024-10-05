package com.ravan.foodie.autoreserve.ui.model

import com.ravan.foodie.order.domain.model.MealType

enum class ReserveStatus {
    SUCCESS,
    FAILURE
}

data class ReserveResultInfoRowUIModel(
    val foodName: String,
    val status: ReserveStatus,
    val message: String,
    val mealType: MealType,
)

fun Result<String>.toReserveResultInfoRowUIModel(
    mealType: MealType,
    foodName: String,
): ReserveResultInfoRowUIModel {
    return this.fold(
        onSuccess = {
            ReserveResultInfoRowUIModel(
                foodName = foodName,
                status = ReserveStatus.SUCCESS,
                message = it,
                mealType = mealType
            )
        }, onFailure = {
            ReserveResultInfoRowUIModel(
                foodName = foodName,
                status = ReserveStatus.FAILURE,
                message = it.message ?: "Unknown error",
                mealType = mealType
            )
        }
    )
}