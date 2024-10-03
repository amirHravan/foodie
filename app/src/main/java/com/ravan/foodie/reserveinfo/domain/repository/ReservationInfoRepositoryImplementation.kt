package com.ravan.foodie.reserveinfo.domain.repository

import android.util.Log
import com.ravan.foodie.reserveinfo.api.ReserveInfoApi
import com.ravan.foodie.reserveinfo.api.dto.toForgetCode
import com.ravan.foodie.reserveinfo.api.dto.toReservationInfo
import com.ravan.foodie.reserveinfo.domain.model.ForgetCode
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

class ReservationInfoRepositoryImplementation(
    private val api: ReserveInfoApi,
//    private val forgetCodeDao: ForgetCodeDao,
) : ReservationInfoRepository {

    private val forgetCodeCache = mutableMapOf<Int, String>()

    override suspend fun getReservationInfo(weekStartDate: String): Result<ReservationInfo> {
        return try {
            val result = api.getReserveInformation(weekStartDate)
            if (result.isSuccessful) {
                Result.success(result.payload!!.toReservationInfo())
            } else {
                Result.failure(Exception(result.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("ReservationInfoRepository", "Error ${e.message}")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }

    override suspend fun getForgetCode(
        reserveId: Int,
        dailySale: Boolean,
    ): Result<ForgetCode> {
        val forgetCode = forgetCodeCache[reserveId]?.let {
            Result.success(ForgetCode(reserveId = reserveId, code = it))
        } ?: run {
            try {
                val result = api.getForgetCode(reserveId = reserveId, dailySale = dailySale)
                if (result.isSuccessful) {
                    val forgetCode = result.payload!!.toForgetCode(reserveId)
                    forgetCodeCache[reserveId] = forgetCode.code
                    Result.success(forgetCode)
                } else {
                    Result.failure(Exception(result.getErrorMessage()))
                }
            } catch (e: Exception) {
                Log.e("ForgetCodeRepository", "Error: $e")
                Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
            }
        }

        return forgetCode
    }

    override fun invalidateCache() {

        forgetCodeCache.clear()
    }

    override fun getMapCache(): Map<Int, String> {
        return forgetCodeCache
    }

}