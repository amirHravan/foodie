package com.ravan.foodie.login.api.dto

import com.ravan.foodie.login.domain.model.SamadToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadTokenDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("change_password_on_login") val changePasswordOnLogin: Boolean,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("jti") val jti: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("national_code") val nationalCode: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("scope") val scope: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("user_id") val userId: Int
)

fun SamadTokenDto.toSamadToken(): SamadToken {
    return SamadToken(
        accessToken = accessToken,
        expiresIn = expiresIn,
        firstName = firstName,
        lastName = lastName,
        nationalCode = nationalCode,
        refreshToken = refreshToken,
        tokenType = tokenType,
        userId = userId,
    )
}