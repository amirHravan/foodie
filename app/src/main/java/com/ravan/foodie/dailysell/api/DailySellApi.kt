package com.ravan.foodie.dailysell.api

import com.ravan.foodie.dailysell.api.dto.DailySellProgramDto
import com.ravan.foodie.dailysell.api.dto.IdHrefDto
import com.ravan.foodie.dailysell.api.dto.UserDailySalesInfoDto
import com.ravan.foodie.domain.network.SamadResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface DailySellApi {

    @GET("/rest/daily-sell-programs")
    suspend fun getDailySalesProgram(): SamadResponse<DailySellProgramDto>

    @GET("/rest/users/me/reserve-daily-sales")
    suspend fun getUserDailySalesInfo(): SamadResponse<List<UserDailySalesInfoDto>>

    @PUT("/rest/daily-sell-programs/{reserveId}/reserve-daily-sell")
    suspend fun orderDailySale(
        @Path("reserveId") reserveId: Int
    ): SamadResponse<IdHrefDto>

}