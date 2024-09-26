package com.ravan.foodie.profile.domain.repository

import com.ravan.foodie.profile.domain.model.SamadNurtureProfile

interface ProfileRepository {

    suspend fun getNurtureProfile(): Result<SamadNurtureProfile>
}