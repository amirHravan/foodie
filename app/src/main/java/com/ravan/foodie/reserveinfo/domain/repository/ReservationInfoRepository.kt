package com.ravan.foodie.reserveinfo.domain.repository

import com.ravan.foodie.reserveinfo.domain.model.ForgetCode
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

interface ReservationInfoRepository {
    suspend fun getReservationInfo(
        weekStartDate: String = "",
    ): Result<ReservationInfo>

    suspend fun getForgetCode(
        reserveId: Int,
        dailySale: Boolean = false,
    ): Result<ForgetCode>

    fun invalidateCache()

    fun getMapCache(): Map<Int, String>
}