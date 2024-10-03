package com.ravan.foodie.dailysell.ui.model

import com.ravan.foodie.dailysell.domain.model.DailySellProgram
import com.ravan.foodie.dailysell.domain.model.UserDailySales

data class DailySellScreenUIModel(
    val dailySaleCardUIModelList: List<DailySaleCardUIModel>,
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
        }
    )
}