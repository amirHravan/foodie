package com.ravan.foodie.reserveinfo.domain.repository

import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

interface ReservationInfoRepository {
    suspend fun getReservationInfo(
        authenticationToken: String,
    ): Result<ReservationInfo>
}