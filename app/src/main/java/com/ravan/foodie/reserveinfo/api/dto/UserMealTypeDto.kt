package com.ravan.foodie.reserveinfo.api.dto

import com.ravan.foodie.reserveinfo.domain.model.ReservationMealInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMealTypeDto(
    @SerialName("date") val date: String,
    @SerialName("dateTime") val dateTime: Long,
    @SerialName("mealTypeId") val mealTypeId: Int,
    @SerialName("name") val name: String,
    @SerialName("reserve") val reserveDto: ReserveDto?
)

fun UserMealTypeDto.toReservationMealInfo(): ReservationMealInfo? {
    return reserveDto?.let {
        ReservationMealInfo(
            selfText = it.selfName,
            foodText = it.foodNames,
            price = it.price.toLong(),
            consumed = it.consumed,
            mealText = name,
            id = it.id
        )
    }
}