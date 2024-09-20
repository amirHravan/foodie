package com.ravan.foodie.forget.api.dto

import com.ravan.foodie.forget.domain.model.ForgetCode
import kotlinx.serialization.SerialName

data class ForgetCodeDto(
    @SerialName("foodName") val foodName: String,
    @SerialName("foodType") val foodType: String,
    @SerialName("forgotCardCode") val forgotCardCode: String,
    @SerialName("meal") val meal: String,
    @SerialName("remainCount") val remainCount: Int,
    @SerialName("self") val self: String,
    @SerialName("username") val username: String,
)

fun ForgetCodeDto.toForgetCode(): ForgetCode {
    return ForgetCode(
        forgotCardCode
    )
}