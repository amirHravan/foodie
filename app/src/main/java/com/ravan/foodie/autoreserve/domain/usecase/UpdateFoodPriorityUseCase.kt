package com.ravan.foodie.autoreserve.domain.usecase

import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository

class UpdateFoodPriorityUseCase(
    private val repository: AutoReserveRepository,
) {
    suspend operator fun invoke(
        food: AutoReserveFoodPriority,
        priority: Int
    ) {
        repository.updateFoodPriority(id = food.id, priority = priority)
    }

}
