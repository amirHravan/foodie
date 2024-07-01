package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.Self

data class SelfRowUIModel(
    val name: String,
    val id: Int,
)

fun Self.toSelfRowUIModel(): SelfRowUIModel {
    return SelfRowUIModel(
        name = name,
        id = id,
    )
}