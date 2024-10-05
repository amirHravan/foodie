package com.ravan.foodie.autoreserve.domain.usecase

import com.ravan.foodie.autoreserve.db.dao.model.toAutoReserveFoodPriority
import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository

class GetAllFoodsUseCase (
    private val repository: AutoReserveRepository
){
    suspend operator fun invoke(
    ): Result<List<AutoReserveFoodPriority>> {
        return try {
            Result.success(repository.getAllFoodPriorities().map { it.toAutoReserveFoodPriority() })
        } catch (e: Exception) {
            Result.failure(Exception("Failed to get all foods"))
        }
    }
}