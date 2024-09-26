package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.repository.DomainRepository

class CheckTokenValidationUseCase(
    val repository: DomainRepository,
) {

    suspend operator fun invoke(token: String): Result<Unit> {
        return repository.checkTokenValidation(token)
    }
}