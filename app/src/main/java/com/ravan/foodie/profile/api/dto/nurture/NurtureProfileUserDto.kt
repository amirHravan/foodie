package com.ravan.foodie.profile.api.dto.nurture

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NurtureProfileUserDto(
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("firstName") val firstName: String,
    @SerialName("gender") val gender: String,
    @SerialName("id") val id: Int,
    @SerialName("lastName") val lastName: String,
    @SerialName("nationalCode") val nationalCode: String,
    @SerialName("personnel") val personnel: Boolean,
    @SerialName("username") val username: String
)