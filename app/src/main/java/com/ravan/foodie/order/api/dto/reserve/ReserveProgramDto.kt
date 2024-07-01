package com.ravan.foodie.order.api.dto.reserve

import com.ravan.foodie.order.domain.model.WeekReservableProgram
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReserveProgramDto(
    @SerialName("mealTypes") val mealTypeDtoList: List<MealTypeDto>,
    @SerialName("selfWeekPrograms") val selfWeekProgramDtoList: List<List<SelfWeekProgramDto>>,
    @SerialName("userId") val userId: Int,
    @SerialName("userWeekReserves") val userWeekReserveDtos: List<UserWeekReserveDto>
)

fun ReserveProgramDto.toWeekReserveProgram(): WeekReservableProgram {
    return WeekReservableProgram(
        userId = userId,
        selfWeekProgram = selfWeekProgramDtoList.map {
            it.toSelfDayReservableProgram(userWeekReserveDtos)
        }
    )
}