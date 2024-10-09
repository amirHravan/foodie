package com.ravan.foodie.reserveinfo.api.dto

import com.ravan.foodie.reserveinfo.domain.model.ReservationDayInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeekDayDto(
    @SerialName("date") val date: String = "",
    @SerialName("dateJStr") val dateJStr: String = "",
//    @SerialName("day") val day: String,
    @SerialName("dayTranslated") val dayTranslated: String = "",
    @SerialName("mealTypes") val mealTypeDtoList: List<UserMealTypeDto> = emptyList()
)

fun WeekDayDto.toReservationDayInfo(): ReservationDayInfo {
    return ReservationDayInfo(
        name = dayTranslated,
        iranianDate = dateJStr,
        date = date,
        mealInfo = mealTypeDtoList.mapNotNull { it.toReservationMealInfo() }
            .filter { !it.hasPassed && !it.consumed }
    )
}