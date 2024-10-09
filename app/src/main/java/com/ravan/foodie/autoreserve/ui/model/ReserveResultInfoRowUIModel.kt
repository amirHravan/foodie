package com.ravan.foodie.autoreserve.ui.model

import com.ravan.foodie.order.domain.model.MealType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

enum class ReserveStatus {
    SUCCESS,
    FAILURE
}

data class ReserveResultInfoRowUIModel(
    val foodName: String,
    val status: ReserveStatus,
    val message: String,
    val mealType: MealType,
    val dayName: String,
    val choices: ImmutableList<String>,
)

fun Result<String>.toReserveResultInfoRowUIModel(
    mealType: MealType,
    foodName: String,
    dayName: String,
    choices: List<String>,
): ReserveResultInfoRowUIModel {
    return this.fold(
        onSuccess = {
            ReserveResultInfoRowUIModel(
                foodName = foodName,
                status = ReserveStatus.SUCCESS,
                message = it,
                mealType = mealType,
                dayName = dayName,
                choices = choices.toImmutableList()
            )
        }, onFailure = {
            ReserveResultInfoRowUIModel(
                foodName = foodName,
                status = ReserveStatus.FAILURE,
                message = it.message ?: "عامممم ارورش معلوم نیست.\u200D",
                mealType = mealType,
                dayName = dayName,
                choices = choices.toImmutableList()
            )
        }
    )
}