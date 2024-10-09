package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.domain.model.ReservableFoodDetail

data class OrderFoodDetailUIModel(
    val programId: Int,
    val selfId: Int,
    val mealTypeId: Int,
    val foodTypeId: Int,
    val foodName: String,
    val price: Long,
    val isSelected: Boolean = false,
    val isDisable: Boolean = false,
    val hasPassed: Boolean = false,
)

fun ReservableFoodDetail.toReserveFoodDetailUIModel(): OrderFoodDetailUIModel {
    return OrderFoodDetailUIModel(
        programId = programId,
        selfId = selfId,
        mealTypeId = mealTypeId,
        foodTypeId = foodTypeId,
        foodName = foodName,
        price = price,
        isSelected = isReserved,
        isDisable = isDisabled,
        hasPassed = hasPassed,
    )
}