package com.ravan.foodie.profile.ui.model

import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.profile.domain.model.CreditTransfer

data class CreditTransferUIModel(
    val amount: String,
    val date: String,
    val isPositive: Boolean,
)

fun CreditTransfer.toCreditTransferUIModel(): CreditTransferUIModel {
    return CreditTransferUIModel(
        amount = amount.toString().toLocalNumber(),
        date = date.split(" ")[0].replace('-', '/').toLocalNumber(),
        isPositive = amount > 0
    )
}