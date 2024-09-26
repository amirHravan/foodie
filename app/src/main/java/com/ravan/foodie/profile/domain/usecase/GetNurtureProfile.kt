package com.ravan.foodie.profile.domain.usecase

import com.ravan.foodie.profile.domain.model.SamadNurtureProfile
import com.ravan.foodie.profile.domain.repository.ProfileRepository

class GetNurtureProfile(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(
    ): Result<SamadNurtureProfile> {
        return repository.getNurtureProfile()
    }
}