package com.ravan.foodie.autoreserve.domain.usecase

import com.ravan.foodie.autoreserve.db.model.toAutoReserveDays
import com.ravan.foodie.autoreserve.domain.model.AutoReserveDays
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository

class GetAllSelectedDaysUseCase(
    private val repository: AutoReserveRepository
) {
    suspend operator fun invoke(): Result<AutoReserveDays> {
//        return try {
//            Result.success(repository.getAllReserveDays()?.toAutoReserveDays() ?: AutoReserveDays(
//                days = mutableListOf()
//            ))
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
        return Result.success(repository.getAllReserveDays()?.toAutoReserveDays() ?: AutoReserveDays(
                days = mutableListOf()
            ))
    }


}