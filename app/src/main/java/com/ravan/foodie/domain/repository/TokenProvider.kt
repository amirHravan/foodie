package com.ravan.foodie.domain.repository

import com.ravan.foodie.domain.api.TokenApi
import com.ravan.foodie.login.api.dto.toSamadToken
import com.ravan.foodie.login.domain.model.SamadToken

class TokenProvider(
    private val tokenApi: TokenApi,
) {
    private var token: SamadToken? = null

    fun getSamadToken(): SamadToken? {
        return token
    }

    fun setSamadToken(token: SamadToken) {
        this.token = token
    }

    suspend fun refreshAccessToken(
        defaultAccessToken: String,
    ): Result<SamadToken> {
        val refreshToken = token?.refreshToken ?: return Result.failure(Exception("توکن زایید!"))
        val response = tokenApi.refreshAccessToken(
            token = defaultAccessToken,
            refreshToken = refreshToken,
        )
        return if (response.isSuccessful) {
            val newToken =
                response.body()?.toSamadToken() ?: return Result.failure(Exception("توکن زایید!"))
            token = newToken
            Result.success(newToken)
        } else {
            Result.failure(Exception("توکن زایید!"))
        }
    }
}

