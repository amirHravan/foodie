package com.ravan.foodie.order.domain.usecase

import com.ravan.foodie.order.api.dto.self.toSelf
import com.ravan.foodie.order.domain.model.AvailableSelfs
import com.ravan.foodie.order.domain.repository.OrderFoodRepository

class GetAvailableSelfs(
    val repository: OrderFoodRepository,
) {
    suspend operator fun invoke(
        token: String,
    ): Result<AvailableSelfs> {
        return repository.getAvailableSelfs(token)
            .map { AvailableSelfs(it.map { selfDto -> selfDto.toSelf() }) }
    }
}