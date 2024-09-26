package com.ravan.foodie.domain.network

import android.util.Log
import com.ravan.foodie.domain.repository.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

private const val DEFAULT_ACCESS_TOKEN = "Basic c2FtYWQtbW9iaWxlOnNhbWFkLW1vYmlsZS1zZWNyZXQ="

class AuthInterceptor(
    private val tokenProvider: TokenProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider.getSamadToken()?.fullToken ?: DEFAULT_ACCESS_TOKEN

        Log.d("AuthInterceptor", "Token: $token")

        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", token.toString())
            .build()

        val response = chain.proceed(modifiedRequest)

//        if (response.code == 401) {
//            // Handle token refreshing
//            synchronized(this) {
//                val newToken = tokenApi.refreshToken().execute().body()?.token
//                newToken?.let {
//                    tokenProvider.saveToken(it)
//                    // Retry the request with new token
//                    val retryRequest = originalRequest.newBuilder()
//                        .addHeader("Authorization", "Bearer $newToken")
//                        .build()
//                    response = chain.proceed(retryRequest)
//                }
//            }
//        }
        return response
    }
}
