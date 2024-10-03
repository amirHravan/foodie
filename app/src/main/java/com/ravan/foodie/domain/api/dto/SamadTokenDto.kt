package com.ravan.foodie.domain.api.dto

import com.ravan.foodie.domain.model.SamadToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadTokenDto(
    @SerialName("access_token") val accessToken: String = "",
    @SerialName("refresh_token") val refreshToken: String = "",
    @SerialName("token_type") val tokenType: String = "",
//    @SerialName("expires_in") val expiresIn: Int = 0,
//    @SerialName("first_name") val firstName: String = "",
//    @SerialName("last_name") val lastName: String = "",
//    @SerialName("user_id") val userId: Int = 0,
//    @SerialName("change_password_on_login") val changePasswordOnLogin: Boolean,
//    @SerialName("jti") val jti: String,
//    @SerialName("national_code") val nationalCode: String,
//    @SerialName("scope") val scope: String,
)

fun SamadTokenDto.toSamadToken(): SamadToken {
    return SamadToken(
        accessToken = "$tokenType $accessToken",
        refreshToken = refreshToken,
    )
}