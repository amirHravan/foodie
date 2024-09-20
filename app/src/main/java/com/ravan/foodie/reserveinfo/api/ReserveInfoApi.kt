package com.ravan.foodie.reserveinfo.api

import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.reserveinfo.api.dto.ReservationInformationDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReserveInfoApi {

    @GET("rest/reserves")
    suspend fun getReserveInformation(
        @Header("Authorization") token: String,
        @Query("weekStartDate") weekStartDate: String,
    ): SamadResponse<ReservationInformationDto>
}