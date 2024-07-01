package com.ravan.foodie.profile.domain.repository

import com.ravan.foodie.profile.domain.model.SamadNurtureProfile

interface ProfileRepository {
    suspend fun checkTokenValidation(token: String): Result<Unit>

    suspend fun getNurtureProfile(token: String): Result<SamadNurtureProfile>
}