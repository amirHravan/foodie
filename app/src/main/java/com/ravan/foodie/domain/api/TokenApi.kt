package com.ravan.foodie.domain.api

import com.ravan.foodie.domain.api.dto.SamadTokenDto
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {

    @POST("oauth/token")
    suspend fun refreshAccessToken(
        @Query("refresh_token") refreshToken: String,
        @Header("Authorization") token: String = "Basic c2FtYWQtbW9iaWxlOnNhbWFkLW1vYmlsZS1zZWNyZXQ=",
        @Query("grant_type") grantType: String = "refresh_token",
    ): Response<SamadTokenDto>

    @POST("oauth/token")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
        @Header("Authorization") token: String = "Basic c2FtYWQtbW9iaWxlOnNhbWFkLW1vYmlsZS1zZWNyZXQ=",
        @Query("grant_type") grantType: String = "password",
        @Query("scope") scope: String = "read+write"
    ): Response<SamadTokenDto>

}