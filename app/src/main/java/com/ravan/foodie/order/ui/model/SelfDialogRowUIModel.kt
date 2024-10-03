package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.Self

data class SelfDialogRowUIModel(
    val name: String,
    val id: Int,
)

fun Self.toSelfRowUIModel(): SelfDialogRowUIModel {
    return SelfDialogRowUIModel(
        name = name,
        id = id,
    )
}