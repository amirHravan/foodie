package com.ravan.foodie.dailysell.domain.repository

import android.util.Log
import com.ravan.foodie.dailysell.api.DailySellApi
import com.ravan.foodie.dailysell.api.dto.toDailySellProgram
import com.ravan.foodie.dailysell.api.dto.toUserDailySales
import com.ravan.foodie.dailysell.domain.model.DailySellProgram
import com.ravan.foodie.dailysell.domain.model.UserDailySales
import com.ravan.foodie.domain.network.SamadResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class DailySellRepositoryImplementation(
    private val dailySellApi: DailySellApi,
    private val json: Json,
) : DailySellRepository {

    override suspend fun getDailySellProgram(): Result<DailySellProgram> {
        return try {
            val response = dailySellApi.getDailySalesProgram()
            if (response.isSuccessful) {
                Result.success(response.payload!!.toDailySellProgram())
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("DailySellRepository", "getDailySellProgram: ${e.message}")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }

    override suspend fun getUserDailySales(): Result<UserDailySales> {
        return try {
            val response = dailySellApi.getUserDailySalesInfo()
            if (response.isSuccessful) {
                Result.success(response.payload!!.toUserDailySales())
            } else {
                Result.failure(Throwable(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("DailySellRepository", "getUserDailySales: ${e.message}")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }

    override suspend fun orderDailySellItem(reserveId: Int): Result<Unit> {
        return try {
            val response = dailySellApi.orderDailySale(reserveId = reserveId)
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
            Log.e("DailySellRepository", "orderDailySellItem: ${e.message}")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }

}