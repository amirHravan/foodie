package com.ravan.foodie.dailysell.domain.repository

import com.ravan.foodie.dailysell.domain.model.DailySellProgram
import com.ravan.foodie.dailysell.domain.model.UserDailySales

interface DailySellRepository {

    suspend fun getDailySellProgram(): Result<DailySellProgram>

    suspend fun getUserDailySales(): Result<UserDailySales>

    suspend fun orderDailySellItem(
        reserveId: Int
    ): Result<Unit>
}