package com.ravan.foodie.autoreserve.domain.model

import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.domain.util.DaysOfWeek

data class AutoReserveDays(
    val days: List<DaysOfWeek>
)

fun AutoReserveDays.toAutoReserveDaysDao() = AutoReserveDaysEntity(
    days = days.map { it.name }
)