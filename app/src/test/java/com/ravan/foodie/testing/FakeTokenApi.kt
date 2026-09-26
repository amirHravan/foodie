package com.ravan.foodie.testing

import com.ravan.foodie.domain.api.TokenApi
import com.ravan.foodie.domain.api.dto.SamadTokenDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.IOException

/**
 * Samad's OAuth endpoint: [passwords] are the accounts that exist, [validRefreshTokens] the
 * refresh tokens it still accepts. Each successful call issues fresh tokens.
 */
class FakeTokenApi(
    val passwords: MutableMap<String, String> = mutableMapOf(),
    val validRefreshTokens: MutableSet<String> = mutableSetOf(),
) : TokenApi {
    var networkDown = false
    val loginCalls = mutableListOf<String>()
    val refreshCalls = mutableListOf<String>()
    private var issued = 0

    override suspend fun login(
        username: String,
        password: String,
        token: String,
        grantType: String,
        scope: String,
    ): Response<SamadTokenDto> {
        loginCalls += username
        if (networkDown) throw IOException("offline")
        return if (passwords[username] == password) issue(username) else unauthorized()
    }

    override suspend fun refreshAccessToken(
        refreshToken: String,
        token: String,
        grantType: String,
    ): Response<SamadTokenDto> {
        refreshCalls += refreshToken
        if (networkDown) throw IOException("offline")
        if (!validRefreshTokens.remove(refreshToken)) return unauthorized()
        return issue(refreshToken.substringBefore(":"))
    }

    private fun issue(username: String): Response<SamadTokenDto> {
        issued++
        val refresh = "$username:refresh$issued"
        validRefreshTokens += refresh
        return Response.success(
            SamadTokenDto(
                accessToken = "$username:access$issued",
                refreshToken = refresh,
                tokenType = "bearer",
            )
        )
    }

    private fun unauthorized(): Response<SamadTokenDto> =
        Response.error(400, "{}".toResponseBody("application/json".toMediaType()))
}
