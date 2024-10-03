package com.ravan.foodie.dailysell.ui.model

import com.ravan.foodie.dailysell.domain.model.UserDailySaleInfo

data class UserDailySaleCardUIModel(
    val consumed: Boolean,
    val id: Int,
    val forgetCode: String?,
)

fun UserDailySaleInfo.toUserDailySaleCardUIModel(
    forgetCode: String?
): UserDailySaleCardUIModel {
    return UserDailySaleCardUIModel(
        consumed = consumed,
        id = id,
        forgetCode = forgetCode,
    )
}

