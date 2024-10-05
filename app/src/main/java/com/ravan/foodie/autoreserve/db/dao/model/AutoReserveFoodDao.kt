package com.ravan.foodie.autoreserve.db.dao.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority

@Entity(tableName = "food_priority")
data class AutoReserveFoodDao(
    @PrimaryKey val id: Int,
    val name: String,
    var priority: Int // 1 to 5 priority
)

fun AutoReserveFoodDao.toAutoReserveFoodPriority() = AutoReserveFoodPriority(
    id = id,
    name = name,
    priority = priority
)
