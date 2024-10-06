package com.ravan.foodie.autoreserve.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravan.foodie.autoreserve.domain.model.AutoReserveDays
import com.ravan.foodie.domain.util.DaysOfWeek

@Entity(tableName = "auto_reserve_days")
data class AutoReserveDaysEntity(
    @PrimaryKey val id: Int = 0,
    val days: List<String>
)

fun AutoReserveDaysEntity.toAutoReserveDays() = AutoReserveDays(
    days = days.map { DaysOfWeek.fromName(it) }
)