package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.WeekReservableProgram
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class OrderScreenUIModel(
    val orderCardUIModelList: ImmutableList<OrderCardUIModel>
)


fun WeekReservableProgram.toReservableScreenUIModel(
): OrderScreenUIModel {
    return OrderScreenUIModel(
        orderCardUIModelList = selfWeekProgram.map { it.toReserveCardUIModel() }
            .filter { it.reserveInfoList.isNotEmpty() }.toImmutableList()
    )
}