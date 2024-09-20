package com.ravan.foodie.login.domain.repository

import com.ravan.foodie.login.domain.model.SamadToken

interface LoginRepository {
    suspend fun checkUserNamePassword(userName: String, password: String): Result<SamadToken>
}