package com.ravan.foodie.login.domain.repository

import android.util.Log
import com.ravan.foodie.login.api.LoginApi
import com.ravan.foodie.login.api.dto.toSamadToken
import com.ravan.foodie.login.domain.model.SamadToken

class LoginRepositoryImplementation(
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun checkUserNamePassword(
        userName: String,
        password: String
    ): Result<SamadToken> {
        val result = loginApi.login(username = userName, password = password)
        return if (result.isSuccessful)
            Result.success(result.body()!!.toSamadToken())
        else {
            Log.e("LoginRepository", "Error: ${result.errorBody()?.string()}")
            Result.failure(Exception("Error: ${result.errorBody()}"))
        }
    }

}