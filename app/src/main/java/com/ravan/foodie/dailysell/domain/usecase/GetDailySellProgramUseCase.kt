package com.ravan.foodie.dailysell.domain.usecase

import com.ravan.foodie.dailysell.domain.model.DailySellProgram
import com.ravan.foodie.dailysell.domain.repository.DailySellRepository

class GetDailySellProgramUseCase(
    private val repository: DailySellRepository
) {
    suspend operator fun invoke(): Result<DailySellProgram> {
        return repository.getDailySellProgram()
    }
}