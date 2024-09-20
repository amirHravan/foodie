package com.ravan.foodie.order.api.dto.reserve

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealTypeDto(
    @SerialName("abbreviation") val abbreviation: String,
    @SerialName("disPriority") val disPriority: Int,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

