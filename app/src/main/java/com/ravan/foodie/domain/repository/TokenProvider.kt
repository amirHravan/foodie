package com.ravan.foodie.domain.repository

import android.util.Log
import com.ravan.foodie.account.domain.model.AuthException
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.domain.api.TokenApi
import com.ravan.foodie.domain.api.dto.SamadTokenDto
import com.ravan.foodie.domain.api.dto.toSamadToken
import com.ravan.foodie.domain.model.SamadToken
import kotlinx.coroutines.CancellationException
import retrofit2.Response

private const val NETWORK_ERROR = "به سرور وصل نمی‌تونیم بشیم. اینترنتت اوکیه؟"
private const val INVALID_CREDENTIALS = "نام‌ کاربری / رمزعبور نادرست است"
private const val BROKEN_TOKEN = "توکن زایید!"

/**
 * Talks to Samad's OAuth endpoint and keeps each account's tokens in [AccountStore].
 */
class TokenProvider(
    private val tokenApi: TokenApi,
    private val accountStore: AccountStore,
) {

    fun activeUsername(): String? = accountStore.active?.username

    fun getSamadToken(username: String? = activeUsername()): SamadToken? =
        username?.let { accountStore.find(it)?.token }

    /**
     * Refreshes [username]'s access token and saves it to that account, even if another account
     * became active while the request was running.
     */
    suspend fun refreshAccessToken(username: String): Result<SamadToken> {
        val refreshToken = accountStore.find(username)?.refreshToken?.takeIf { it.isNotEmpty() }
            ?: return Result.failure(AuthException.InvalidCredentials(BROKEN_TOKEN))
        return requestRefresh(refreshToken).onSuccess { token ->
            accountStore.update(username) {
                it.copy(accessToken = token.accessToken, refreshToken = token.refreshToken)
            }
        }
    }

    /** Exchanges a refresh token for new tokens without saving them. */
    suspend fun requestRefresh(refreshToken: String): Result<SamadToken> = request {
        tokenApi.refreshAccessToken(refreshToken = refreshToken)
    }

    /** Logs in with a username and password without saving anything. */
    suspend fun requestLogin(username: String, password: String): Result<SamadToken> = request {
        tokenApi.login(username = username, password = password)
    }

    private suspend fun request(
        call: suspend () -> Response<SamadTokenDto>,
    ): Result<SamadToken> {
        val response = try {
            call()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("TokenProvider", "token request failed: ${e.message}")
            return Result.failure(AuthException.Network(NETWORK_ERROR))
        }
        if (!response.isSuccessful) {
            return Result.failure(
                if (response.code() >= 500) {
                    AuthException.Network(NETWORK_ERROR)
                } else {
                    AuthException.InvalidCredentials(INVALID_CREDENTIALS)
                }
            )
        }
        val token = response.body()?.toSamadToken()
            ?: return Result.failure(AuthException.Network(BROKEN_TOKEN))
        return Result.success(token)
    }
}
