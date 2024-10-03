package com.ravan.foodie.domain.network

import android.util.Log
import com.ravan.foodie.domain.repository.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

private const val DEFAULT_ACCESS_TOKEN = "Basic c2FtYWQtbW9iaWxlOnNhbWFkLW1vYmlsZS1zZWNyZXQ="

class AuthInterceptor(
    private val tokenProvider: TokenProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider.getSamadToken()?.accessToken ?: DEFAULT_ACCESS_TOKEN

        Log.d("AuthInterceptor", "Token: $token")

        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", token)
            .build()

        var response = chain.proceed(modifiedRequest)

        if (response.code == 401) {
            // Handle token refreshing
            synchronized(this) {
                val newToken = runBlocking {
                    tokenProvider.refreshAccessToken()
                        .fold(
                            onSuccess = {
                                it
                            },
                            onFailure = {
                                null
                            }
                        )
                }
                newToken?.let {
                    response.close()

                    val retryRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", newToken.accessToken)
                        .build()

                    response = chain.proceed(retryRequest)
                }
            }
        }
        return response
    }
}
