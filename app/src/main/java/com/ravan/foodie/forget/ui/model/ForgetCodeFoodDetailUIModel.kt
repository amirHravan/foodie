package com.ravan.foodie.forget.ui.model

data class ForgetCodeFoodDetailUIModel(
    val selfName: String,
    val foodName: String,
    val reserveId: Int,
    val forgetCode: String? = null,
    val consumed: Boolean = false,
)