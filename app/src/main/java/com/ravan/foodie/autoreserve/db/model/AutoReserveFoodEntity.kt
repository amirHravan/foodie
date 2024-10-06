package com.ravan.foodie.autoreserve.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority

@Entity(tableName = "food_priority")
data class AutoReserveFoodEntity(
    @PrimaryKey() val id: Int,
    val name: String,
    var priority: Int
)

fun AutoReserveFoodEntity.toAutoReserveFoodPriority() = AutoReserveFoodPriority(
    id = id,
    name = name,
    priority = priority
)
