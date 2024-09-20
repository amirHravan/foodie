package com.ravan.foodie.order.api.dto.reserve

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodDto(
    @SerialName("averageScore") val averageScore: Double,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("voteCount") val voteCount: Int
)