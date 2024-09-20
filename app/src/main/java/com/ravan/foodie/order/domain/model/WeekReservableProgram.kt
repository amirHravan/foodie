package com.ravan.foodie.order.domain.model

data class WeekReservableProgram(
    val userId: Int,
    val selfWeekProgram: List<SelfDayReservableProgram>,
)

fun WeekReservableProgram?.merge(other: WeekReservableProgram?): WeekReservableProgram {
    if (this == null && other == null) {
        return WeekReservableProgram(userId = 0, selfWeekProgram = emptyList())
    }
    if (this == null) return other!!
    if (other == null) return this

    return WeekReservableProgram(
        userId = this.userId,
        selfWeekProgram = this.selfWeekProgram.orEmpty() + other.selfWeekProgram.orEmpty()
    )
}