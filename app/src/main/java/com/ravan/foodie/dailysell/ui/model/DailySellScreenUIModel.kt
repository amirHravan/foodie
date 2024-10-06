package com.ravan.foodie.dailysell.ui.model

import com.ravan.foodie.dailysell.domain.model.DailySellProgram
import com.ravan.foodie.dailysell.domain.model.UserDailySales
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class DailySellScreenUIModel(
    val dailySaleCardUIModelList: ImmutableList<DailySaleCardUIModel>,
)

fun DailySellProgram.toDailySellScreenUIModel(
    forgetCodeMap: Map<Int, String>,
    userDailySales: UserDailySales?
): DailySellScreenUIModel {
    return DailySellScreenUIModel(
        dailySaleCardUIModelList = dailySaleInfoList.map { dailySellItem ->
            dailySellItem.toDailySaleCardUIModel(
                forgetCodeMap = forgetCodeMap,
                userDailySaleInfo = userDailySales?.dailySaleInfoList?.find { userDailySaleInfo -> userDailySaleInfo.dailySellProgramId == dailySellItem.id },
            )
        }.toImmutableList()
    )
}