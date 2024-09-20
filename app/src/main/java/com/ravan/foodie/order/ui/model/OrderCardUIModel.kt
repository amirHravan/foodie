package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.domain.model.SelfDayReservableProgram

data class OrderCardUIModel(
    val farsiDayName: String,
    val date: String,
    val dayName: String,
    val reserveInfoList: Map<MealType, List<OrderFoodDetailUIModel>>

)

fun SelfDayReservableProgram.toReserveCardUIModel(): OrderCardUIModel {
    return OrderCardUIModel(
        this.farsiDayName,
        this.date,
        this.dayName,
        this.reserveInfoList.mapValues { (_, value) ->
            value.map { it.toReserveFoodDetailUIModel() }
        }
    )
}