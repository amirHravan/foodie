package com.ravan.foodie.reserveinfo.domain.model

import com.ravan.foodie.reserveinfo.db.model.ForgetCodeEntity

data class ForgetCode(
    val reserveId: Int,
    val code: String,
    val isValid: Boolean,
)

fun ForgetCode.toForgetCodeEntity() = ForgetCodeEntity(
    reserveId = reserveId,
    code = code,
    isValid = isValid,
)