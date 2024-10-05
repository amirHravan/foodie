package com.ravan.foodie.autoreserve.domain.model

import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveFoodDao

data class AutoReserveFoodPriority(
    val id: Int,
    val name: String,
    val priority: Int,
)

fun AutoReserveFoodPriority.toAutoReserveFoodDao(
    newPriority: Int = priority
): AutoReserveFoodDao {
    return AutoReserveFoodDao(
        id = id,
        name = name,
        priority = newPriority,
    )
}
