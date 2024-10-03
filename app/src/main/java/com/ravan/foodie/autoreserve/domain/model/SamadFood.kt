package com.ravan.foodie.autoreserve.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "samad_foods")
data class SamadFood(
    @PrimaryKey val id: Int,
    val name: String,
    var priority: Int // 1 to 5 priority
)

