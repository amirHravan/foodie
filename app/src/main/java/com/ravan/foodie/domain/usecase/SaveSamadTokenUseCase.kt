package com.ravan.foodie.domain.usecase

import com.ravan.foodie.domain.repository.DomainRepository

class SaveSamadTokenUseCase(
    private val domainRepository: DomainRepository
) {
    operator fun invoke(token: String) {
        domainRepository.token = token
    }
}