package com.ravan.foodie.order.domain.repository

import com.ravan.foodie.order.api.OrderApi
import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestData

class OrderFoodRepositoryImplementation(
    private val api: OrderApi
) : OrderFoodRepository {

    override suspend fun reserveFood(
        authenticationToken: String,
        reserveRequestData: ReserveRequestData,
        programId: Int
    ): Result<Unit> {
        return try {
            val response = api.reserveFood(
                authenticationToken,
                programId,
                reserveRequestData
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Throwable(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getReserveProgram(
        authenticationToken: String,
        selfId: Int,
        weekStartDate: String,
    ): Result<ReserveProgramDto> {
        return try {
            val response = api.getReserveProgram(
                authenticationToken,
                selfId,
                weekStartDate
            )
            if (response.isSuccessful) {
                Result.success(response.payload!!)
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("هیچ برنامه غذایی‌ای برای این سلف تعریف نشده است."))
        }

    }

    override suspend fun getAvailableSelfs(
        authenticationToken: String
    ): Result<List<SelfDto>> {
        return try {
            val response = api.getAvailableSelfs(authenticationToken)

            return if (response.isSuccessful) {
                Result.success(response.payload!!)
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("هیچ سلفی برای شما تعریف نشده."))
        }
    }

}