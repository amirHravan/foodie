package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.domain.model.SelfDayReservableProgram
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class OrderCardUIModel(
    val farsiDayName: String,
    val date: String,
    val dayName: String,
    val reserveInfoList: Map<MealType, ImmutableList<OrderFoodDetailUIModel>>

)

fun SelfDayReservableProgram.toReserveCardUIModel(): OrderCardUIModel {
    return OrderCardUIModel(
        this.farsiDayName,
        this.date,
        this.dayName,
        this.reserveInfoList.mapValues { (_, value) ->
            value.map { it.toReserveFoodDetailUIModel() }.toImmutableList()
        }.filter { (_, value) -> value.isNotEmpty() }
    )
}