package com.ravan.foodie.reserveinfo.domain.repository

import android.util.Log
import com.ravan.foodie.reserveinfo.api.ReserveInfoApi
import com.ravan.foodie.reserveinfo.api.dto.toReservationInfo
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

class ReservationInfoRepositoryImplementation(
    val api: ReserveInfoApi,
) : ReservationInfoRepository {

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

}