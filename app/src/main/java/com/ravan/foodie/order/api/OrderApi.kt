package com.ravan.foodie.order.api

import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApi {

    @GET("rest/programs/v2")
    suspend fun getReserveProgram(
        @Header("Authorization") token: String,
        @Query("selfId") selfId: Int,
        @Query("weekStartDate") date: String,
    ): SamadResponse<ReserveProgramDto>

    @PUT("rest/reserves/{programId}/reserve")
    suspend fun reserveFood(
        @Header("Authorization") token: String,
        @Path("programId") programId: Int,
        @Body reservableFoodDetail: ReserveRequestData,
    ): Response<Unit>

    @GET("rest/selfs")
    suspend fun getAvailableSelfs(
        @Header("Authorization") token: String,
    ): SamadResponse<List<SelfDto>>
}