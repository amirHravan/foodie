package com.ravan.foodie.dailysell.api.dto

import com.ravan.foodie.dailysell.domain.model.SamadTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SamadTimeDto(
    @SerialName("hour") val hour: Int = 0,
    @SerialName("minute") val minute: Int = 0,
    @SerialName("timeStr") val timeStr: String = "00:00"
//    @SerialName("cal") val cal: String,
)

fun SamadTimeDto.toSamadTime(): SamadTime {
    return SamadTime(
        hour = hour,
        minute = minute,
        timeStr = timeStr
    )
}