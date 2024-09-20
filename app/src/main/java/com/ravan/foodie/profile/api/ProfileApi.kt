package com.ravan.foodie.profile.api

import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.profile.api.dto.me.SamadUserDto
import com.ravan.foodie.profile.api.dto.nurture.NurtureProfileDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {

    @GET("rest/users/me")
    suspend fun pingServer(
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("rest/users/nurture-profiles")
    suspend fun getNurtureProfile(
        @Header("Authorization") token: String
    ): SamadResponse<NurtureProfileDto>

    @GET("rest/users/me")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): SamadResponse<SamadUserDto>
}