package com.ravan.foodie.order.domain.repository

import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestBodyData

interface OrderFoodRepository {

    suspend fun reserveFood(
        reserveRequestBodyData: ReserveRequestBodyData,
        programId: Int,
    ): Result<Unit>

    suspend fun getReserveProgram(
        selfId: Int,
        weekStartDate: String,
    ): Result<ReserveProgramDto>

    suspend fun getAvailableSelfs(
    ): Result<List<SelfDto>>
}