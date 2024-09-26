package com.ravan.foodie.forget.domain.usecase

import com.ravan.foodie.forget.domain.model.ForgetCode
import com.ravan.foodie.forget.domain.repository.ForgetCodeRepository

class GetForgetCodeUseCase(
    private val repository: ForgetCodeRepository,
) {
    suspend operator fun invoke(
        reserveId: Int,
    ): Result<ForgetCode> {
        return repository.getForgetCode(
            reserveId = reserveId
        )
    }
}