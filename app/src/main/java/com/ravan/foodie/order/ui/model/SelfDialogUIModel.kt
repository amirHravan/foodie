package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.AvailableSelfs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class SelfDialogUIModel(
    val selfs: ImmutableList<SelfDialogRowUIModel>
)

fun AvailableSelfs.toSelfDialogUIModel(): SelfDialogUIModel {
    return SelfDialogUIModel(
        selfs = selfs.map { it.toSelfRowUIModel() }.toImmutableList()
    )
}
