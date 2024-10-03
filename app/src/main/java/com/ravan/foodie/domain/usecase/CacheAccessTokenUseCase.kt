package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.repository.TokenProvider
import com.ravan.foodie.login.domain.model.SamadToken

class CacheAccessTokenUseCase(
    private val tokenProvider: TokenProvider
) {
    operator fun invoke(token: SamadToken) {
        tokenProvider.setSamadToken(token = token)
    }
}