package com.ravan.foodie.order.domain.usecase

import com.ravan.foodie.order.api.dto.reserve.toWeekReserveProgram
import com.ravan.foodie.order.domain.model.WeekReservableProgram
import com.ravan.foodie.order.domain.repository.OrderFoodRepository

class GetReservableProgramUseCase(
    private val repository: OrderFoodRepository,
) {
    suspend operator fun invoke(
        selfId: Int,
        weekStartDate: String,
    ): Result<WeekReservableProgram> {
        return repository.getReserveProgram(
            selfId = selfId,
            weekStartDate = weekStartDate,
        ).map { it.toWeekReserveProgram() }
    }
}