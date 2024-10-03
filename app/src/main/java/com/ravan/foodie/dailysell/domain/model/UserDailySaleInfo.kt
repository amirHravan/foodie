package com.ravan.foodie.dailysell.domain.model

data class UserDailySaleInfo(
    val consumed: Boolean,
    val dailySellProgramId: Int,
    val finishTime: SamadTime?,
    val foodNames: String,
    val foodTypeTitle: String,
    val id: Int,
    val price: Int,
    val programDate: String,
    val remainCount: Int,
    val selfName: String,
    val startTime: SamadTime?
)