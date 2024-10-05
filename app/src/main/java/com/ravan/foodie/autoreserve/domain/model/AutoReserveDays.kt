package com.ravan.foodie.autoreserve.domain.model

import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveDaysDao
import com.ravan.foodie.domain.util.DaysOfWeek

data class AutoReserveDays(
    val days: List<DaysOfWeek>
)

fun AutoReserveDays.toAutoReserveDaysDao() = AutoReserveDaysDao(
    days = days.map { it.name }
)