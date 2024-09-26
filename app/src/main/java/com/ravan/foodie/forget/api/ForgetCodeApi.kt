package com.ravan.foodie.forget.api

import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.forget.api.dto.ForgetCodeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForgetCodeApi {

    @GET("rest/forget-card-codes/print")
    suspend fun getForgetCode(
        @Query("reserveId") reserveId: Int,
        @Query("count") count: Int = 1,
        @Query("dailySale") dailySale: Boolean = false
    ): SamadResponse<ForgetCodeDto>

}