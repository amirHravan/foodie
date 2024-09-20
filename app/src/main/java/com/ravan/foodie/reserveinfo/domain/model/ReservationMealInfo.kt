package com.ravan.foodie.reserveinfo.domain.model

data class ReservationMealInfo(
    val selfText: String,
    val foodText: String,
    val mealText: String,
    val price: Long,
    val consumed: Boolean = false,
)
