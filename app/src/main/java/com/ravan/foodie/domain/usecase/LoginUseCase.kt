package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.model.SamadToken
import com.ravan.foodie.domain.repository.TokenProvider
import com.ravan.foodie.domain.util.toEnglishNumber

class LoginUseCase(
    private val tokenProvider: TokenProvider,
) {

    suspend operator fun invoke(username: String, password: String): Result<SamadToken> {
        return tokenProvider.login(
            username.toEnglishNumber(),
            password.toEnglishNumber()
        )
    }
}