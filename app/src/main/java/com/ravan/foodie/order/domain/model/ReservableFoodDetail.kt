package com.ravan.foodie.order.domain.model

data class ReservableFoodDetail(
    val foodTypeId: Int,
    val mealTypeId: Int,
    val programId: Int,
    val selfId: Int,
    val foodName: String,
    val price: Long,
    val isReserved: Boolean = true,
    val isDisabled: Boolean = false,
    val hasPassed: Boolean = false,
)