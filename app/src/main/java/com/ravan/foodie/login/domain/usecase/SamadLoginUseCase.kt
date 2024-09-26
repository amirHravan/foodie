package com.ravan.foodie.login.domain.usecase

import com.ravan.foodie.domain.usecase.SaveSamadTokenUseCase
import com.ravan.foodie.login.domain.model.SamadToken
import com.ravan.foodie.login.domain.repository.LoginRepository

class SamadLoginUseCase(
    private val repository: LoginRepository,
    private val savedSamadTokenUseCase: SaveSamadTokenUseCase,
) {

    suspend operator fun invoke(username: String, password: String): Result<SamadToken> {
        return repository.checkUserNamePassword(username, password).onSuccess { token ->
            savedSamadTokenUseCase(token)
        }
    }
}