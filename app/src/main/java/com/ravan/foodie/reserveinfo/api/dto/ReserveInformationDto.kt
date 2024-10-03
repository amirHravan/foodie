package com.ravan.foodie.reserveinfo.api.dto

import com.ravan.foodie.reserveinfo.domain.model.ReservationDayInfo
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservationInformationDto(
    @SerialName("remainCredit") val remainCredit: Int = 0,
    @SerialName("weekDays") val weekDayDtoList: List<WeekDayDto> = emptyList()
//    @SerialName("mealTypes") val mealTypeDtoList: List<MealTypeDto>,
)

fun ReservationInformationDto.toReservationInfo(): ReservationInfo {
    return ReservationInfo(
        dayInfoList = this.toReservationDayInfoList(),
        remainCredit = remainCredit.toLong(),
    )
}

fun ReservationInformationDto.toReservationDayInfoList(): List<ReservationDayInfo> {
    return weekDayDtoList.map { it.toReservationDayInfo() }.filter { it.mealInfo.isNotEmpty() }
}