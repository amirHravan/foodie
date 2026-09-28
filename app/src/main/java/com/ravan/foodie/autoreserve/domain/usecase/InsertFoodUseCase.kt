package com.ravan.foodie.autoreserve.domain.usecase

import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository

class InsertFoodUseCase(
    private val repository: AutoReserveRepository,
) {
    suspend operator fun invoke(
        food: String,
    ) {
        repository.insertFood(name = food, priority = 0)
    }
}
