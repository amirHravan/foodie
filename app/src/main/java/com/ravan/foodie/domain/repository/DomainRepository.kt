package com.ravan.foodie.domain.repository

interface DomainRepository {
    suspend fun checkTokenValidation(token: String): Result<Unit>
}