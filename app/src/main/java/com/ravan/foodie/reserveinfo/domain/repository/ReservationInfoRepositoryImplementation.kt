package com.ravan.foodie.reserveinfo.domain.repository

import com.ravan.foodie.reserveinfo.api.ReserveInfoApi
import com.ravan.foodie.reserveinfo.api.dto.toReservationInfo
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

class ReservationInfoRepositoryImplementation(
    val api: ReserveInfoApi,
) : ReservationInfoRepository {

    override suspend fun getReservationInfo(authenticationToken: String, weekStartDate: String): Result<ReservationInfo> {
        return try {
            val result = api.getReserveInformation(authenticationToken, weekStartDate)
            if (result.isSuccessful) {
                Result.success(result.payload!!.toReservationInfo())
            } else {
                Result.failure(Exception(result.getErrorMessage()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}