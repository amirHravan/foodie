package com.ravan.foodie.domain.repository

interface DomainRepository {
    suspend fun checkTokenValidation(): Result<Unit>
}