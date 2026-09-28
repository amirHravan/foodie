package com.ravan.foodie.order.ui.model

import com.ravan.foodie.order.domain.model.MealType
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderFoodKeyTest {

    private fun food(programId: Int, foodTypeId: Int, isSelected: Boolean = false) =
        OrderFoodDetailUIModel(
            programId = programId,
            selfId = 1,
            mealTypeId = 1,
            foodTypeId = foodTypeId,
            foodName = "food $programId/$foodTypeId",
            price = 1000,
            isSelected = isSelected,
        )

    private val kabab = food(programId = 10, foodTypeId = 1)
    private val gheyme = food(programId = 10, foodTypeId = 2, isSelected = true)
    private val dinner = food(programId = 11, foodTypeId = 1)

    private val model = OrderScreenUIModel(
        orderCardUIModelList = persistentListOf(
            OrderCardUIModel(
                farsiDayName = "شنبه",
                date = "2026-09-26",
                dayName = "Saturday",
                reserveInfoList = mapOf(
                    MealType.LUNCH to persistentListOf(kabab, gheyme),
                    MealType.DINNER to persistentListOf(dinner),
                ),
            )
        )
    )

    private fun OrderScreenUIModel.find(key: OrderFoodKey) =
        orderCardUIModelList.flatMap { it.reserveInfoList.values.flatten() }.single { it.key == key }

    @Test
    fun `selects only the matching food`() {
        val result = model.withFoodSelected(kabab.key, isSelected = true)

        assertTrue(result.find(kabab.key).isSelected)
        assertTrue(result.find(gheyme.key).isSelected)
        assertFalse(result.find(dinner.key).isSelected)
    }

    @Test
    fun `deselects the matching food`() {
        val result = model.withFoodSelected(gheyme.key, isSelected = false)

        assertFalse(result.find(gheyme.key).isSelected)
        assertFalse(result.find(kabab.key).isSelected)
    }

    @Test
    fun `is idempotent`() {
        val once = model.withFoodSelected(kabab.key, isSelected = true)
        val twice = once.withFoodSelected(kabab.key, isSelected = true)

        assertEquals(once, twice)
    }

    @Test
    fun `returns the same instance when no food matches`() {
        val result = model.withFoodSelected(OrderFoodKey(99, 1, 1), isSelected = true)

        assertSame(model, result)
    }

    @Test
    fun `key uses the ids the reserve request is sent with`() {
        assertEquals(OrderFoodKey(programId = 10, mealTypeId = 1, foodTypeId = 2), gheyme.key)
    }
}
