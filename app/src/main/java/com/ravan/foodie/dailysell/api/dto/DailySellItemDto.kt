package com.ravan.foodie.dailysell.api.dto

import com.ravan.foodie.dailysell.domain.model.DailySellItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailySellItemDto(
    @SerialName("count") val count: Int = -1,
    @SerialName("finishTime") val finishTime: SamadTimeDto? = null,
    @SerialName("foodNames") val foodNames: String = "",
    @SerialName("foodTypeTitle") val foodTypeTitle: String = "",
    @SerialName("id") val id: Int = -1,
    @SerialName("mealTypeName") val mealTypeName: String = "",
    @SerialName("price") val price: Int = -1,
    @SerialName("programDate") val programDate: String = "",
    @SerialName("selfName") val selfName: String = "",
    @SerialName("soldCount") val soldCount: Int = -1,
    @SerialName("startTime") val startTime: SamadTimeDto? = null,
    @SerialName("totalCount") val totalCount: Int = -1,
//    @SerialName("maxValue") val maxValue: Int,
//    @SerialName("mealTypeId") val mealTypeId: Int,
//    @SerialName("minValue") val minValue: Int,
//    @SerialName("selectedCount") val selectedCount: Int,
//    @SerialName("selfCode") val selfCode: String,
)

fun DailySellItemDto.toDailySellItem(): DailySellItem {
    return DailySellItem(
        count = count,
        finishTime = finishTime?.toSamadTime(),
        foodNames = foodNames,
        foodTypeTitle = foodTypeTitle,
        id = id,
        mealTypeName = mealTypeName,
        price = price,
        programDate = programDate,
        selfName = selfName,
        soldCount = soldCount,
        startTime = startTime?.toSamadTime(),
        totalCount = totalCount
    )
}