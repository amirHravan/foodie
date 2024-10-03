package com.ravan.foodie.domain.api

import retrofit2.Response
import retrofit2.http.GET

interface DomainApi {

    @GET("rest/users/me")
    suspend fun pingServer(): Response<Unit>
}