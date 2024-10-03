package com.ravan.foodie.login.domain.usecase

import com.ravan.foodie.domain.usecase.CacheAccessTokenUseCase
import com.ravan.foodie.domain.util.toEnglishNumber
import com.ravan.foodie.login.domain.model.SamadToken
import com.ravan.foodie.login.domain.repository.LoginRepository

class SamadLoginUseCase(
    private val repository: LoginRepository,
    private val savedSamadTokenUseCase: CacheAccessTokenUseCase,
) {

    suspend operator fun invoke(username: String, password: String): Result<SamadToken> {
        return repository.checkUserNamePassword(
            username.toEnglishNumber(),
            password.toEnglishNumber()
        ).onSuccess { token ->
            savedSamadTokenUseCase(token)
        }
    }
}