package com.ravan.foodie.forget.domain.usecase

import com.ravan.foodie.forget.domain.repository.ForgetCodeRepository

class GetForgetCodeUseCase(
    private val repository: ForgetCodeRepository,
)