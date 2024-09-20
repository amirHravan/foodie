package com.ravan.foodie.reserveinfo.domain.usecase

import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import com.ravan.foodie.reserveinfo.domain.repository.ReservationInfoRepository

class GetReservationInformationUseCase(
    private val repository: ReservationInfoRepository,
) {
    suspend operator fun invoke(authenticationToken: String, weekStartDate: String = ""): Result<ReservationInfo> {
        return repository.getReservationInfo(authenticationToken, weekStartDate)
    }
}