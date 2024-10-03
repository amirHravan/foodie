package com.ravan.foodie.order.domain.repository

import android.util.Log
import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.order.api.OrderApi
import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestBodyData
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class OrderFoodRepositoryImplementation(
    private val api: OrderApi,
    private val json: Json,
) : OrderFoodRepository {

    override suspend fun reserveFood(
        reserveRequestBodyData: ReserveRequestBodyData,
        programId: Int
    ): Result<Unit> {
        return try {
            val response = api.reserveFood(
                programId = programId,
                reservableFoodDetail = reserveRequestBodyData
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: HttpException) {
            Log.e("OrderFoodRepository", "reserveFood: ${e.message}")
            if (e.code() == 400) {
                val errorBody = e.response()?.errorBody()?.string()
                if (errorBody != null) {
                    val error = json.decodeFromString<SamadResponse<Unit>>(errorBody)
                    Result.failure(Throwable(error.getErrorMessage()))
                } else {
                    Result.failure(e)
                }
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Log.e("OrderFoodRepository", "reserveFood: ${e.message}")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }

    override suspend fun getReserveProgram(
        selfId: Int,
        weekStartDate: String,
    ): Result<ReserveProgramDto> {
        return try {
            val response = api.getReserveProgram(
                selfId = selfId,
                date = weekStartDate
            )
            if (response.isSuccessful) {
                Result.success(response.payload!!)
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("OrderFoodRepository:getReservableProgram", "Error ${e.message}")
            Result.failure(Throwable("هیچ برنامه غذایی‌ای برای این سلف تعریف نشده است."))
        }

    }

    override suspend fun getAvailableSelfs(
    ): Result<List<SelfDto>> {
        return try {
            val response = api.getAvailableSelfs()

            return if (response.isSuccessful) {
                Result.success(response.payload!!)
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("OrderFoodRepository:getAvailableSelfs", "Error ${e.message}")
            Result.failure(Throwable("هیچ سلفی برای شما تعریف نشده."))
        }
    }

}