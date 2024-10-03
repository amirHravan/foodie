package com.ravan.foodie.credit.domain.usecase

import com.ravan.foodie.credit.domain.model.RedirectLoginAsToken
import com.ravan.foodie.credit.domain.repository.CreditRepository

class GetRedirectLoginAsTokenUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(): Result<RedirectLoginAsToken> {
        return creditRepository.getLoginAsToken()
    }
}