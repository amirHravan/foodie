package com.ravan.foodie.domain.repository

import android.util.Log
import com.ravan.foodie.domain.api.TokenApi
import com.ravan.foodie.domain.api.dto.toSamadToken
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.model.SamadToken
import com.ravan.foodie.domain.util.SharedPrefKeys

class TokenProvider(
    private val tokenApi: TokenApi,
    private val preferencesManager: PreferencesManager,
) {
    private var token: SamadToken? = null

    init {
        val accessToken = preferencesManager.getString(SharedPrefKeys.AccessToken.key, "")
        val refreshToken = preferencesManager.getString(SharedPrefKeys.RefreshToken.key, "")
        if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
            token = SamadToken(accessToken, refreshToken)
        }
    }

    fun getSamadToken(): SamadToken? {
        return token
    }

    fun setSamadToken(token: SamadToken) {
        this.token = token
    }

    suspend fun refreshAccessToken(): Result<SamadToken> {
        val refreshToken = token?.refreshToken
            ?: return Result.failure(Exception("اوضاع خیطه! اپ رو می‌شه ببندی دوباره باز کنی؟"))
        val response = try {
            tokenApi.refreshAccessToken(
                refreshToken = refreshToken,
            )
        } catch (e: Exception) {
            Log.e("TokenProvider", "refreshAccessToken: ${e.message}")
            return Result.failure(Exception("به سرور وصل نمی\u200Cتونیم بشیم. اینترنتت اوکیه؟"))
        }
        return if (response.isSuccessful) {
            val newToken =
                response.body()?.toSamadToken() ?: return Result.failure(Exception("توکن زایید!"))
            token = newToken

            sharedPrefSaveToken(newToken)

            Result.success(newToken)
        } else {
            Result.failure(Exception("${response.errorBody() ?: "نام\u200C کاربری / رمزعبور نادرست است"}"))
        }
    }

    private fun sharedPrefSaveToken(newToken: SamadToken) {
        preferencesManager.putString(SharedPrefKeys.AccessToken.key, newToken.accessToken)
        preferencesManager.putString(SharedPrefKeys.RefreshToken.key, newToken.refreshToken)
    }

    suspend fun login(
        userName: String,
        password: String
    ): Result<SamadToken> {
        val result = try {
            tokenApi.login(username = userName, password = password)
        } catch (e: Exception) {
            Log.e("LoginRepository", "login: ${e.message}")
            return Result.failure(Exception("به سرور وصل نمی\u200Cتونیم بشیم. اینترنتت اوکیه؟"))
        }
        return if (result.isSuccessful) {
            val newToken =
                result.body()?.toSamadToken() ?: return Result.failure(Exception("توکن زایید!"))
            token = newToken
            sharedPrefSaveToken(newToken)
            Result.success(newToken)
        } else {
            Log.e("LoginRepository", "Error: ${result.errorBody()?.string()}")
            Result.failure(Exception("نام\u200C کاربری / رمزعبور نادرست است"))
        }
    }

}

