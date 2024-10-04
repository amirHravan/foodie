package com.ravan.foodie.autoreserve.ui.model

enum class ReserveStatus {
    SUCCESS,
    FAILURE
}

data class ReserveResultInfoRowUIModel(
    val foodName: String,
    val status: ReserveStatus,
    val message: String,
)
