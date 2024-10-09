package com.ravan.foodie.autoreserve.domain.usecase

import android.util.Log
import com.ravan.foodie.autoreserve.domain.model.AutoReserveDays
import com.ravan.foodie.autoreserve.domain.model.toAutoReserveDaysDao
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepository

class UpdateAutoReserveDaysUseCase(
    private val repository: AutoReserveRepository
) {
    suspend operator fun invoke(
        days: AutoReserveDays,
    ) {
        repository.updateReserveDays(days.toAutoReserveDaysDao())
    }

}