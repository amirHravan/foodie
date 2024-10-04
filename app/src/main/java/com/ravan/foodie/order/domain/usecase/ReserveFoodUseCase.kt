package com.ravan.foodie.order.domain.usecase

import com.ravan.foodie.order.domain.model.ReserveRequestBodyData
import com.ravan.foodie.order.domain.repository.OrderFoodRepository

class ReserveFoodUseCase(
    private val repository: OrderFoodRepository
) {

    suspend operator fun invoke(
        foodTypeId: Int,
        mealTypeId: Int,
        programId: Int,
        selected: Boolean,
    ): Result<String> {
        return repository.reserveFood(
            reserveRequestBodyData = ReserveRequestBodyData(
                foodTypeId = foodTypeId,
                mealTypeId = mealTypeId,
                selected = selected,
                freeFoodSelected = false,
                selectedCount = 1,
            ),
            programId = programId
        )

    }
}