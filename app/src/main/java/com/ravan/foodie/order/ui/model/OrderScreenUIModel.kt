package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.WeekReservableProgram

data class OrderScreenUIModel(
    val orderCardUIModelList: List<OrderCardUIModel>
)


fun WeekReservableProgram.toReservableScreenUIModel(
): OrderScreenUIModel {
    return OrderScreenUIModel(
        orderCardUIModelList = selfWeekProgram.map { it.toReserveCardUIModel() }
    )
}