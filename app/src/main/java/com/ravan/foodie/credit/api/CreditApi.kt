package com.ravan.foodie.credit.api

import com.ravan.foodie.credit.api.dto.RedirectLoginAsTokenDto
import com.ravan.foodie.domain.network.SamadResponse
import retrofit2.http.GET

interface CreditApi {

    @GET("rest/users/me/login-token")
    suspend fun getRedirectLoginAsToken(): SamadResponse<RedirectLoginAsTokenDto>


}