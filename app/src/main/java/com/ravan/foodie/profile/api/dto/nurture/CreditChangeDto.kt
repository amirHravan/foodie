package com.ravan.foodie.profile.api.dto.nurture

import com.ravan.foodie.profile.domain.model.CreditTransfer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditChangeDto(
    @SerialName("amount") val amount: Int,
    @SerialName("createDate") val createDate: String
)

fun CreditChangeDto.toCreditTransfer(): CreditTransfer {
    return CreditTransfer(
        amount = amount,
        date = createDate,
    )
}