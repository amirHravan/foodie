package com.ravan.foodie.domain.api

import com.ravan.foodie.login.api.dto.SamadTokenDto
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {

    @POST("oauth/token")
    suspend fun refreshAccessToken(
        @Header("Authorization") token: String,
        @Query("refresh_token") refreshToken: String,
        @Query("grant_type") grantType: String = "refresh_token",
    ): Response<SamadTokenDto>

}