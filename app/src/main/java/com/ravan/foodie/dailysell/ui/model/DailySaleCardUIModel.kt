package com.ravan.foodie.dailysell.ui.model

import com.ravan.foodie.dailysell.domain.model.DailySellItem
import com.ravan.foodie.dailysell.domain.model.UserDailySaleInfo
import com.ravan.foodie.domain.util.toLocalNumber

data class DailySaleCardUIModel(
    val id: Int,
    val count: String,
    val soldCount: String,
    val foodName: String,
    val mealTypeName: String,
    val price: String,
    val selfName: String?,
    val startTime: String?,
    val finishTime: String?,
    val showOrderButton: Boolean,
    val userDailySaleInfo: UserDailySaleCardUIModel?
)

fun DailySellItem.toDailySaleCardUIModel(
    forgetCodeMap: Map<Int, String>,
    userDailySaleInfo: UserDailySaleInfo?
): DailySaleCardUIModel {
    return DailySaleCardUIModel(
        id = id,
        count = count.toString().toLocalNumber(),
        soldCount = soldCount.toString().toLocalNumber(),
        foodName = foodNames,
        mealTypeName = mealTypeName,
        price = price.toString().toLocalNumber(),
        selfName = selfName,
        startTime = startTime?.timeStr,
        finishTime = finishTime?.timeStr,
        showOrderButton = userDailySaleInfo == null,
        userDailySaleInfo = userDailySaleInfo?.toUserDailySaleCardUIModel(forgetCodeMap[userDailySaleInfo.id])
    )
}