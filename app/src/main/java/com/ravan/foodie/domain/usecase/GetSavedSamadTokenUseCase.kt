package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.repository.DomainRepository

class GetSavedSamadTokenUseCase(
    private val domainRepository: DomainRepository
) {
    operator fun invoke(): String? {
        return domainRepository.token
    }
}