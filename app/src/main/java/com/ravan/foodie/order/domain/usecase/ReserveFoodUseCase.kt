package com.ravan.foodie.order.domain.usecase

import com.ravan.foodie.order.domain.model.ReserveRequestData
import com.ravan.foodie.order.domain.repository.OrderFoodRepository

class ReserveFoodUseCase(
    private val repository: OrderFoodRepository
) {

    suspend operator fun invoke(
        foodTypeId: Int,
        mealTypeId: Int,
        programId: Int,
        selected: Boolean,
        token: String,
    ): Result<Unit> {
        return repository.reserveFood(
            authenticationToken = token,
            reserveRequestData = ReserveRequestData(
                foodTypeId = foodTypeId,
                mealTypeId = mealTypeId,
                selected = selected
            ),
            programId = programId
        )

    }
}