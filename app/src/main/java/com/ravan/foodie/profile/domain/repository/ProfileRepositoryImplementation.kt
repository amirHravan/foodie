package com.ravan.foodie.profile.domain.repository

import android.util.Log
import com.ravan.foodie.profile.api.ProfileApi
import com.ravan.foodie.profile.api.dto.nurture.toSamadNurtureProfile
import com.ravan.foodie.profile.domain.model.SamadNurtureProfile

class ProfileRepositoryImplementation(
    private val api: ProfileApi,
) : ProfileRepository {

    override suspend fun getNurtureProfile(): Result<SamadNurtureProfile> {
        return try {
            val response = api.getNurtureProfile()
            if (response.isSuccessful) {
                Result.success(response.payload!!.toSamadNurtureProfile())
            } else {
                Result.failure(Exception(response.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error: $e")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }
}