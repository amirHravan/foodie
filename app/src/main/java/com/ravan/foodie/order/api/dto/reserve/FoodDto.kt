package com.ravan.foodie.order.api.dto.reserve

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
//    @SerialName("averageScore") val averageScore: Double,
//    @SerialName("voteCount") val voteCount: Int
)