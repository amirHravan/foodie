package com.ravan.foodie.reserveinfo.domain.model

data class ReservationMealInfo(
    val selfText: String,
    val foodText: String,
    val mealText: String,
    val price: Long,
    val id: Int,
    val consumed: Boolean = false,
    val hasPassed: Boolean = false,
)
