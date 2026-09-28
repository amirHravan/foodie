package com.ravan.foodie.order.ui.model

import kotlinx.collections.immutable.toImmutableList

/**
 * Identifies a single reservable food. These are the same ids the reserve request is sent with.
 */
data class OrderFoodKey(
    val programId: Int,
    val mealTypeId: Int,
    val foodTypeId: Int,
)

val OrderFoodDetailUIModel.key: OrderFoodKey
    get() = OrderFoodKey(
        programId = programId,
        mealTypeId = mealTypeId,
        foodTypeId = foodTypeId,
    )

/**
 * Returns a copy where the food matching [key] has [OrderFoodDetailUIModel.isSelected] set to
 * [isSelected]. Used to show a reserve/cancel result before the program is fetched again.
 * Returns the same instance when no food matches.
 */
fun OrderScreenUIModel.withFoodSelected(
    key: OrderFoodKey,
    isSelected: Boolean,
): OrderScreenUIModel {
    var changed = false
    val cards = orderCardUIModelList.map { card ->
        val reserveInfoList = card.reserveInfoList.mapValues { (_, foods) ->
            if (foods.none { it.key == key }) {
                foods
            } else {
                changed = true
                foods.map {
                    if (it.key == key) it.copy(isSelected = isSelected) else it
                }.toImmutableList()
            }
        }
        card.copy(reserveInfoList = reserveInfoList)
    }
    return if (changed) copy(orderCardUIModelList = cards.toImmutableList()) else this
}
