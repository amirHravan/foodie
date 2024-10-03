package com.ravan.foodie.order.api.dto.reserve

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealTypeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
//    @SerialName("disPriority") val disPriority: Int,
//    @SerialName("abbreviation") val abbreviation: String,
)

