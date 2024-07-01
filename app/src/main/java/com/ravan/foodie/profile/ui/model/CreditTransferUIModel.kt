package com.ravan.foodie.profile.ui.model

import com.ravan.foodie.profile.domain.model.CreditTransfer

data class CreditTransferUIModel(
    val amount: Int,
    val date: String,
)

fun CreditTransfer.toCreditTransferUIModel(): CreditTransferUIModel {
    return CreditTransferUIModel(
        amount = amount,
        date = date.split(" ")[0].replace('-', '/')
    )
}