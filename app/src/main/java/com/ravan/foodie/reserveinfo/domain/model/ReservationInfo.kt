package com.ravan.foodie.reserveinfo.domain.model

data class ReservationInfo(
    val remainCredit: Long,
    val dayInfoList: List<ReservationDayInfo>,
)
