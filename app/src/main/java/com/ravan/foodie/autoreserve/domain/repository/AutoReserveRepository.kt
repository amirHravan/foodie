package com.ravan.foodie.autoreserve.domain.repository

import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

interface AutoReserveRepository {

    suspend fun getAllFoodPriorities(): List<AutoReserveFoodEntity>

    suspend fun updateFoodPriority(autoReserveFoodEntity: AutoReserveFoodEntity)

    suspend fun getAllReserveDays(): AutoReserveDaysEntity?

    suspend fun updateReserveDays(autoReserveDaysEntity: AutoReserveDaysEntity)
}