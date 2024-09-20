package com.ravan.foodie.reserveinfo.domain.model

data class ReservationDayInfo(
    val name: String,
    val date: String,
    val mealInfo: List<ReservationMealInfo?>,
)
