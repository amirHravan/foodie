package com.ravan.foodie.reserveinfo.domain.usecase

import com.ravan.foodie.reserveinfo.domain.repository.ReservationInfoRepository

class GetForgetCodeMapCacheUseCase(
    private val repository: ReservationInfoRepository
) {

    operator fun invoke(): Map<Int, String> {
        return repository.getMapCache()
    }
}