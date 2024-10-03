package com.ravan.foodie.reserveinfo.domain.usecase

import com.ravan.foodie.reserveinfo.domain.model.ForgetCode
import com.ravan.foodie.reserveinfo.domain.repository.ReservationInfoRepository

class GetForgetCodeUseCase(
    private val repository: ReservationInfoRepository,
) {
    suspend operator fun invoke(
        reserveId: Int,
        dailySale: Boolean = false,
    ): Result<ForgetCode> {
        return repository.getForgetCode(
            reserveId = reserveId,
            dailySale = dailySale
        )
    }
}