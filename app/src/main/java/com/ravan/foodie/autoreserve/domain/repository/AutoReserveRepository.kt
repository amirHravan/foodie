package com.ravan.foodie.autoreserve.domain.repository

import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveDaysDao
import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveFoodDao

interface AutoReserveRepository {

    suspend fun getAllFoodPriorities(): List<AutoReserveFoodDao>

    suspend fun updateFoodPriority(autoReserveFoodDao: AutoReserveFoodDao)

    suspend fun getAllReserveDays(): AutoReserveDaysDao?

    suspend fun updateReserveDays(autoReserveDaysDao: AutoReserveDaysDao)
}