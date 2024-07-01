package com.ravan.foodie.order.domain.model

data class WeekReservableProgram(
    val userId: Int,
    val selfWeekProgram: List<SelfDayReservableProgram>,
)