package com.ravan.foodie.profile.api

import com.ravan.foodie.domain.network.SamadResponse
import com.ravan.foodie.profile.api.dto.me.SamadUserDto
import com.ravan.foodie.profile.api.dto.nurture.NurtureProfileDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {

    @GET("rest/users/nurture-profiles")
    suspend fun getNurtureProfile(
    ): SamadResponse<NurtureProfileDto>

    @GET("rest/users/me")
    suspend fun getUserProfile(
    ): SamadResponse<SamadUserDto>
}