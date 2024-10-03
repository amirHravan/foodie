package com.ravan.foodie.profile.api.dto.nurture

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NurtureProfileUserDto(
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("username") val username: String
//    @SerialName("enabled") val enabled: Boolean,
//    @SerialName("gender") val gender: String,
//    @SerialName("id") val id: Int,
//    @SerialName("nationalCode") val nationalCode: String,
//    @SerialName("personnel") val personnel: Boolean,
)