package com.ravan.foodie.domain.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadResponse<T>(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String? = null,
    @SerialName("messageFa") val messageFa: String? = null,
    @SerialName("payload") val payload: T? = null,
    @SerialName("type") val type: String = "ERROR",
) {
    val isSuccessful = type != "ERROR"

    fun getErrorMessage(): String {
        return messageFa ?: message ?: "زارت!"
    }
}