package com.ravan.foodie.forget.ui.model

import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo

data class ForgetCodeScreenUIModel(
    val forgetCodeItemUIModelList: List<ForgetCodeItemUIModel>
)

fun ReservationInfo.toForgetCodeScreenUIModel(codeMap: Map<Int, String>): ForgetCodeScreenUIModel {
    return ForgetCodeScreenUIModel(
        forgetCodeItemUIModelList = this.dayInfoList.map { it.toForgetCodeItemUIModel(codeMap) }
    )
}
