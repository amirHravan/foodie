package com.ravan.foodie.dailysell.domain.usecase

import com.ravan.foodie.dailysell.domain.model.UserDailySales
import com.ravan.foodie.dailysell.domain.repository.DailySellRepository

class GetUserDailySalesUseCase(
    private val repository: DailySellRepository
) {
    suspend operator fun invoke(): Result<UserDailySales> {
        return repository.getUserDailySales()
    }
}