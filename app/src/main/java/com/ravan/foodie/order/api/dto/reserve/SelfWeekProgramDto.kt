package com.ravan.foodie.order.api.dto.reserve

import com.ravan.foodie.domain.util.toLocalDayName
import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.domain.model.ReservableFoodDetail
import com.ravan.foodie.order.domain.model.SelfDayReservableProgram
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SelfWeekProgramDto(
    @SerialName("cancelRuleViolated") val cancelRuleViolated: Boolean,
    @SerialName("date") val date: String,
    @SerialName("dayTranslated") val dayTranslated: String,
    @SerialName("foodId") val foodId: Int,
    @SerialName("foodName") val foodName: String,
    @SerialName("foodTypeId") val foodTypeId: Int,
    @SerialName("mealTypeId") val mealTypeId: Int,
    @SerialName("price") val price: Int,
    @SerialName("programId") val programId: Int,
    @SerialName("reserveRuleViolated") val reserveRuleViolated: Boolean,
    @SerialName("selfId") val selfId: Int,
    @SerialName("daysDifferenceWithToday") val daysDifferenceWithToday: Int,
//    @SerialName("foodTypeTitle") val foodTypeTitle: String,
//    @SerialName("groupId") val groupId: Int,
//    @SerialName("mealTypeName") val mealTypeName: String,
//    @SerialName("validTotalCount") val validTotalCount: Int
//    @SerialName("buyableFreeFood") val buyableFreeFood: Boolean,
//    @SerialName("buyableFreeFoodToll") val buyableFreeFoodToll: Int,
//    @SerialName("hideBesideFoodInPanel") val hideBesideFoodInPanel: Boolean,
//    @SerialName("hideInPanel") val hideInPanel: Boolean,
//    @SerialName("priorReserveDate") val priorReserveDate: String,
//    @SerialName("programFoodTypes") val programFoodTypeDtos: List<ProgramFoodTypeDto>,
)

fun SelfWeekProgramDto.toReservableFoodDetail(
    isSelected: Boolean
): ReservableFoodDetail {
    return ReservableFoodDetail(
        foodTypeId = foodTypeId,
        mealTypeId = mealTypeId,
        programId = programId,
        selfId = selfId,
        foodName = foodName,
        price = price.toLong(),
        isReserved = isSelected,
        isDisabled = reserveRuleViolated || cancelRuleViolated,
        hasPassed = daysDifferenceWithToday < 0,
    )
}

fun List<SelfWeekProgramDto>.toReservableFoodMap(
    userWeekReserveDtoList: List<UserWeekReserveDto>
): Map<MealType, List<ReservableFoodDetail>> {
    return this.groupBy { MealType.getMealTypeById(it.mealTypeId) }
        .mapValues { (_, selfWeekProgramDtos) ->
            selfWeekProgramDtos.map {
                it.toReservableFoodDetail(
                    userWeekReserveDtoList.any { userWeekReserveDto -> userWeekReserveDto.foodId == it.foodId }
                )
            }.filter { !it.hasPassed }
        }
}

fun List<List<SelfWeekProgramDto>>.toReservableFoodMapList(
    userWeekReserveDtoList: List<UserWeekReserveDto>
): List<Map<MealType, List<ReservableFoodDetail>>> {
    return this.map { selfWeekProgramDtos ->
        selfWeekProgramDtos.toReservableFoodMap(
            userWeekReserveDtoList.filter { it.programDate == selfWeekProgramDtos.firstOrNull()?.date })
    }

}

fun List<SelfWeekProgramDto>.toSelfDayReservableProgram(
    userWeekReserveDtoList: List<UserWeekReserveDto>
): SelfDayReservableProgram {
    val sample = this.firstOrNull() ?: return SelfDayReservableProgram(
        date = "",
        dayName = "",
        farsiDayName = "",
        reserveInfoList = emptyMap()
    )
    return SelfDayReservableProgram(
        date = sample.date,
        dayName = sample.dayTranslated,
        farsiDayName = sample.dayTranslated.toLocalDayName(),
        reserveInfoList = this.toReservableFoodMap(userWeekReserveDtoList),
    )
}

