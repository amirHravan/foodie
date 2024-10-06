package com.ravan.foodie.autoreserve.domain.model

import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

data class AutoReserveFoodPriority(
    val id: Int,
    val name: String,
    val priority: Int,
)

fun AutoReserveFoodPriority.toAutoReserveFoodDao(
    newPriority: Int = priority
): AutoReserveFoodEntity {
    return AutoReserveFoodEntity(
        id = id,
        name = name,
        priority = newPriority,
    )
}
