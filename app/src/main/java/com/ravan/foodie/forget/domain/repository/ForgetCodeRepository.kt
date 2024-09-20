package com.ravan.foodie.forget.domain.repository

import com.ravan.foodie.forget.domain.model.ForgetCode

interface ForgetCodeRepository {

    suspend fun getForgetCode(
        authenticationToken: String,
        reserveId: Int
    ): Result<ForgetCode>

    fun invalidateCache()
}