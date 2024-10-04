package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.model.SamadToken
import com.ravan.foodie.domain.repository.TokenProvider

class CacheAccessTokenUseCase(
    private val tokenProvider: TokenProvider
) {
    operator fun invoke(token: SamadToken) {
        tokenProvider.setSamadToken(token = token)
    }
}