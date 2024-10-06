package com.ravan.foodie.autoreserve.ui.model

import com.ravan.foodie.domain.util.DaysOfWeek
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class AutoReserveScreenUIModel(
    val selectedDaysList: ImmutableList<DaysOfWeek> = emptyList<DaysOfWeek>().toImmutableList()
)
