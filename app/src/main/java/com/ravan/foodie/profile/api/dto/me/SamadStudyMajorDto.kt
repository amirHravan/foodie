package com.ravan.foodie.profile.api.dto.me

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadStudyMajorDto(
    @SerialName("description") val description: String,
    @SerialName("name") val name: String
)