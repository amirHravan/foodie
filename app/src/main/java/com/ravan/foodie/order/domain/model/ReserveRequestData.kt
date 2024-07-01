package com.ravan.foodie.order.domain.model

import kotlinx.serialization.SerialName

data class ReserveRequestData(
    @SerialName("foodTypeId") val foodTypeId: Int,
    @SerialName("mealTypeId") val mealTypeId: Int,
    @SerialName("freeFoodSelected") val freeFoodSelected: Boolean = false,
    @SerialName("selected") val selected: Boolean = true,
    @SerialName("selectedCount") val selectedCount: Int = 1
)