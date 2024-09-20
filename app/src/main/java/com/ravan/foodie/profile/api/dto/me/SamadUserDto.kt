package com.ravan.foodie.profile.api.dto.me

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadUserDto(
    @SerialName("changeProfilePicturePermission") val changeProfilePicturePermission: Boolean,
    @SerialName("email") val email: String,
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("faceMatching") val faceMatching: Boolean,
    @SerialName("faculty") val faculty: Boolean,
    @SerialName("fatherName") val fatherName: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("gender") val gender: String,
    @SerialName("id") val id: Int,
    @SerialName("lastName") val lastName: String,
    @SerialName("nationalCode") val nationalCode: String,
    @SerialName("nurtureCreditProfileId") val nurtureCreditProfileId: String,
    @SerialName("personnel") val personnel: Boolean,
    @SerialName("student") val samadStudentDto: SamadStudentDto,
    @SerialName("username") val username: String
)