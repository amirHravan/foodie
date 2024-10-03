package com.ravan.foodie.dailysell.api.dto

import com.ravan.foodie.dailysell.domain.model.UserDailySaleInfo
import com.ravan.foodie.dailysell.domain.model.UserDailySales
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDailySalesInfoDto(
    @SerialName("consumed") val consumed: Boolean = false,
    @SerialName("dailySellProgramId") val dailySellProgramId: Int = -1,
    @SerialName("foodNames") val foodNames: String = "",
    @SerialName("foodTypeTitle") val foodTypeTitle: String = "",
    @SerialName("id") val id: Int = -1,
    @SerialName("price") val price: Int = -1,
    @SerialName("programDate") val programDate: String = "",
    @SerialName("remainCount") val remainCount: Int = -1,
    @SerialName("selfName") val selfName: String = "",
    @SerialName("finishTime") val finishTime: SamadTimeDto? = null,
    @SerialName("startTime") val startTime: SamadTimeDto? = null,
//    @SerialName("foodTypeId") val foodTypeId: Int,
//    @SerialName("groupId") val groupId: Int,
//    @SerialName("mealTypeId") val mealTypeId: Int,
//    @SerialName("mealTypeName") val mealTypeName: String,
//    @SerialName("programId") val programId: Int,
//    @SerialName("reserveDailySaleId") val reserveDailySaleId: Int,
//    @SerialName("selectedCount") val selectedCount: Int,
//    @SerialName("selfCode") val selfCode: String,
//    @SerialName("selfCodeName") val selfCodeName: String,
//    @SerialName("selfDefId") val selfDefId: Int,
)

fun List<UserDailySalesInfoDto>.toUserDailySales(): UserDailySales {
    return UserDailySales(
        dailySaleInfoList = map { it.toUserDailySaleInfo() }
    )
}

fun UserDailySalesInfoDto.toUserDailySaleInfo(): UserDailySaleInfo {
    return UserDailySaleInfo(
        consumed = consumed,
        dailySellProgramId = dailySellProgramId,
        finishTime = finishTime?.toSamadTime(),
        foodNames = foodNames,
        foodTypeTitle = foodTypeTitle,
        id = id,
        price = price,
        programDate = programDate,
        remainCount = remainCount,
        selfName = selfName,
        startTime = startTime?.toSamadTime()
    )
}