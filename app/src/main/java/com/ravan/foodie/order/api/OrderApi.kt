package com.ravan.foodie.order.api

import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestBodyData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApi {

    @GET("rest/programs/v2")
    suspend fun getReserveProgram(
        @Query("selfId") selfId: Int,
        @Query("weekStartDate") date: String,
    ): SamadResponse<ReserveProgramDto>

    @PUT("rest/reserves/{programId}/reserve")
    suspend fun reserveFood(
        @Path("programId") programId: Int,
        @Body reservableFoodDetail: ReserveRequestBodyData,
    ): Response<Unit>

    @GET("rest/selfs")
    suspend fun getAvailableSelfs(
    ): SamadResponse<List<SelfDto>>
}