package com.ravan.foodie.reserveinfo.domain.model

data class ReservationInfo(
    val remainCredit: Long,
    val dayInfoList: List<ReservationDayInfo>,
) {
    operator fun plus(other: ReservationInfo): ReservationInfo {
        return ReservationInfo(
            remainCredit = remainCredit,
            dayInfoList = dayInfoList + other.dayInfoList
        )
    }
}
