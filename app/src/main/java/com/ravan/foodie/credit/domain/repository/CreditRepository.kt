package com.ravan.foodie.credit.domain.repository

import com.ravan.foodie.credit.domain.model.RedirectLoginAsToken

interface CreditRepository {

    suspend fun getLoginAsToken(): Result<RedirectLoginAsToken>
}