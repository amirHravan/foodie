package com.ravan.foodie.dailysell.domain.model

data class DailySellItem(
    val count: Int,
    val finishTime: SamadTime?,
    val foodNames: String,
    val foodTypeTitle: String,
    val id: Int,
    val mealTypeName: String,
    val price: Int,
    val programDate: String,
    val selfName: String,
    val soldCount: Int,
    val startTime: SamadTime?,
    val totalCount: Int
)
