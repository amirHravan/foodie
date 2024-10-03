package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.repository.DomainRepository

class CheckTokenValidationUseCase(
    val repository: DomainRepository,
) {

    suspend operator fun invoke(): Result<Unit> {
        return repository.checkTokenValidation()
    }
}