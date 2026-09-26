package com.ravan.foodie.autoreserve.domain.repository

import com.ravan.foodie.account.domain.repository.AccountScopedData
import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

/** Food priorities and auto-reserve days of the active account. */
interface AutoReserveRepository : AccountScopedData {

    suspend fun getAllFoodPriorities(): List<AutoReserveFoodEntity>

    suspend fun updateFoodPriority(id: Int, priority: Int)

    suspend fun getAllReserveDays(): AutoReserveDaysEntity?

    suspend fun updateReserveDays(days: List<String>)

    suspend fun insertFood(name: String, priority: Int)
}
