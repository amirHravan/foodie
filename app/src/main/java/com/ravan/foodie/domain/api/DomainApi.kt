package com.ravan.foodie.domain.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface DomainApi {

    @GET("rest/users/me")
    suspend fun pingServer(
        @Header("Authorization") token: String
    ): Response<Unit>
}