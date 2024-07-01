package com.ravan.foodie.reserveinfo.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealTypeDto(
    @SerialName("abbreviation") val abbreviation: String,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("selectFoodTypeIds") val selectFoodTypeIds: String
)




