package com.ravan.foodie.login.api

import com.ravan.foodie.login.api.dto.SamadTokenDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


interface LoginApi {

    @POST("oauth/token")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("grant_type") grantType: String = "password",
        @Query("scope") scope: String = "read+write"
    ): Response<SamadTokenDto>

}