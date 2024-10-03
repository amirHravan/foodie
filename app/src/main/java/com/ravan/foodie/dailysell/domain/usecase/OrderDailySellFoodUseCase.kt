package com.ravan.foodie.dailysell.domain.usecase

import com.ravan.foodie.dailysell.domain.repository.DailySellRepository

class OrderDailySellFoodUseCase(
    private val repository: DailySellRepository,
) {
    suspend operator fun invoke(reserveId: Int): Result<Unit> {
        return repository.orderDailySellItem(reserveId = reserveId)
    }
}