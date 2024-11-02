package com.ravan.foodie.autoreserve.domain.usecase

import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository

class InsertFoodUseCase(
    private val repository: AutoReserveRepository,
) {
    suspend operator fun invoke(
        food: String,
    ) {
        repository.insertFood(AutoReserveFoodEntity(name = food, priority = 0))
    }
}