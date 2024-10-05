package com.ravan.foodie.autoreserve.ui.model

import com.ravan.foodie.domain.util.DaysOfWeek

data class AutoReserveScreenUIModel(
    val selectedDaysList: List<DaysOfWeek> = emptyList()
)
