package com.ravan.foodie.profile.api.dto.nurture

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReceiptDto(
    @SerialName("paymentSystemName") val paymentSystemName: String,
    @SerialName("receiptNumber") val receiptNumber: String
)