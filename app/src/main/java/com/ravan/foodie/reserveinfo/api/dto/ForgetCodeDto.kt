package com.ravan.foodie.reserveinfo.api.dto

import com.ravan.foodie.reserveinfo.domain.model.ForgetCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgetCodeDto(
    @SerialName("forgotCardCode") val forgotCardCode: String = "",
//    @SerialName("valid") val valid: Boolean = true,
//    @SerialName("remainCount") val remainCount: Int = 0,
//    @SerialName("self") val self: String = "",
//    @SerialName("foodName") val foodName: String = "",
//    @SerialName("foodType") val foodType: String = "",
//    @SerialName("meal") val meal: String = "",
//    @SerialName("username") val username: String = "",
)

fun ForgetCodeDto.toForgetCode(foodId: Int): ForgetCode {
    return ForgetCode(
        reserveId = foodId,
        code = forgotCardCode
    )
}