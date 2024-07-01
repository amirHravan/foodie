package com.ravan.foodie.order.domain.model

data class SelfDayReservableProgram(
    val dayName: String,
    val date: String,
    val farsiDayName: String,
    val reserveInfoList: Map<MealType, List<ReservableFoodDetail>>
)