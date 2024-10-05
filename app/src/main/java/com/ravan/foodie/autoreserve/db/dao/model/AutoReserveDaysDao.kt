package com.ravan.foodie.autoreserve.db.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravan.foodie.autoreserve.domain.model.AutoReserveDays
import com.ravan.foodie.domain.util.DaysOfWeek

@Entity(tableName = "auto_reserve_days")
data class AutoReserveDaysDao(
    @PrimaryKey val id: Int = 0,
    val days: List<String>
)

fun AutoReserveDaysDao.toAutoReserveDays() = AutoReserveDays(
    days = days.map { DaysOfWeek.fromName(it) }
)