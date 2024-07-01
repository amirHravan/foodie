package com.ravan.foodie.order.domain.repository

import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestData

interface OrderFoodRepository {

    suspend fun reserveFood(
        authenticationToken: String,
        reserveRequestData: ReserveRequestData,
        programId: Int,
    ): Result<Unit>

    suspend fun getReserveProgram(
        authenticationToken: String,
        selfId: Int,
        weekStartDate: String,
    ): Result<ReserveProgramDto>

    suspend fun getAvailableSelfs(
        authenticationToken: String
    ): Result<List<SelfDto>>
}