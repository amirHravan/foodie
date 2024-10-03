package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.AvailableSelfs

data class SelfDialogUIModel(
    val selfs: List<SelfDialogRowUIModel>
)

fun AvailableSelfs.toSelfDialogUIModel(): SelfDialogUIModel {
    return SelfDialogUIModel(
        selfs = selfs.map { it.toSelfRowUIModel() }
    )
}
