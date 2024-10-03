package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.repository.TokenProvider
import com.ravan.foodie.domain.model.SamadToken

class RefreshAccessTokenUseCase(
    private val tokenProvider: TokenProvider,
) {
    suspend operator fun invoke(): Result<SamadToken> {
        return tokenProvider.refreshAccessToken()
    }
}