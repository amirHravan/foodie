package com.ravan.foodie.order.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReserveRequestBodyData(
    @SerialName("foodTypeId") val foodTypeId: Int,
    @SerialName("mealTypeId") val mealTypeId: Int,
    @SerialName("freeFoodSelected") val freeFoodSelected: Boolean,
    @SerialName("selected") val selected: Boolean,
    @SerialName("selectedCount") val selectedCount: Int
)