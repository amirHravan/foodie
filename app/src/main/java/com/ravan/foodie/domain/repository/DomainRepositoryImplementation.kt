package com.ravan.foodie.domain.repository

import com.ravan.foodie.domain.api.DomainApi
import com.ravan.foodie.domain.model.RavanUser

class DomainRepositoryImplementation(
    private val domainApi: DomainApi,
) : DomainRepository {

    var user: RavanUser? = null

    override suspend fun checkTokenValidation(): Result<Unit> {
        val result = domainApi.pingServer()
        return if (result.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("توکن زایید!"))
        }
    }
}