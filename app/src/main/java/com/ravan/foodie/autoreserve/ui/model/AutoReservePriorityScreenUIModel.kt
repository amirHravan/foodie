package com.ravan.foodie.autoreserve.ui.model

import kotlinx.collections.immutable.ImmutableList

data class AutoReservePriorityScreenUIModel(
    val foodPriorityUIModelList: ImmutableList<FoodPriorityUIModel>,
)