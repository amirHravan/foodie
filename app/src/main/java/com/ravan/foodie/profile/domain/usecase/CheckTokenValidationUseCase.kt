package com.ravan.foodie.profile.domain.usecase

import com.ravan.foodie.profile.domain.repository.ProfileRepository

class CheckTokenValidationUseCase(
    val repository: ProfileRepository,
) {

    suspend operator fun invoke(token: String): Result<Unit> {
        return repository.checkTokenValidation(token)
    }
}