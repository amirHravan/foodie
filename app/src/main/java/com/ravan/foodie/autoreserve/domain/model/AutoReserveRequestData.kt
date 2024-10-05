package com.ravan.foodie.autoreserve.domain.model

data class AutoReserveRequestData(
    val foodName: String,
    val foodTypeId: Int,
    val mealTypeId: Int,
    val programId: Int,
)
