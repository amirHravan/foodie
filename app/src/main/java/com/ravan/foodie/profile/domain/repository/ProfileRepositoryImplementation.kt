package com.ravan.foodie.profile.domain.repository

import com.ravan.foodie.profile.api.ProfileApi
import com.ravan.foodie.profile.api.dto.nurture.toSamadNurtureProfile
import com.ravan.foodie.profile.domain.model.SamadNurtureProfile

class ProfileRepositoryImplementation(
    private val api: ProfileApi,
) : ProfileRepository {

    override suspend fun checkTokenValidation(token: String): Result<Unit> {
        val result = api.pingServer(token)
        return if (result.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("توکن زایید!"))
        }
    }

    override suspend fun getNurtureProfile(token: String): Result<SamadNurtureProfile> {
        return try {
            val response = api.getNurtureProfile(token)
            if (response.isSuccessful) {
                Result.success(response.payload!!.toSamadNurtureProfile())
            } else {
                Result.failure(Exception(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("در دریافت اطلاعات پروفایل هم زاییدیم."))
        }
    }
}