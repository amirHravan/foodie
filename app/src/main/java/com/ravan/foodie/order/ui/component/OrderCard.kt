package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.ui.fixture.orderCardUIModelFixture1
import com.ravan.foodie.order.ui.model.OrderCardUIModel
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel
import com.ravan.foodie.order.ui.model.OrderFoodKey
import com.ravan.foodie.order.ui.model.key
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf


/**
 * @param dayIndex position of the day in the list; part of the lazy item keys so they stay unique
 * even if two days ever share a date.
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.orderCard(
    data: OrderCardUIModel,
    dayIndex: Int,
    pendingFoods: ImmutableSet<OrderFoodKey>,
    onReserveFoodClick: (OrderFoodDetailUIModel) -> Unit,
) {
    if (data.reserveInfoList.isNotEmpty()) {
        stickyHeader(key = "day-$dayIndex-${data.date}") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RavanTheme.colors.background.primary)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = data.farsiDayName,
                    style = RavanTheme.typography.h4,
                    modifier = Modifier,
                    color = RavanTheme.colors.text.onPrimary
                )
            }
            FoodieDivider(
                color = RavanTheme.colors.border.onPrimary,
            )
        }
        items(
            items = data.reserveInfoList.toList(),
            key = { (mealType, _) -> "day-$dayIndex-${data.date}-$mealType" },
        ) { (mealType, reservationFoodDetailList) ->
            OrderMealTypeSection(
                mealType,
                reservationFoodDetailList,
                pendingFoods,
                onReserveFoodClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Composable
private fun OrderMealTypeSection(
    mealType: MealType,
    reservationFoodDetailList: List<OrderFoodDetailUIModel>,
    pendingFoods: ImmutableSet<OrderFoodKey>,
    onReserveFoodClick: (OrderFoodDetailUIModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(RavanTheme.colors.background.primary)
            .padding(8.dp)
            .clip(RavanTheme.shapes.r8)
            .background(RavanTheme.colors.background.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (reservationFoodDetailList.isNotEmpty()) {
            Text(
                text = mealType.getLocalName(),
                style = RavanTheme.typography.h6,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 24.dp)
                    .fillMaxWidth()
            )
            reservationFoodDetailList.forEach { reservationFoodDetail ->
                FoodieDivider(
                    color = RavanTheme.colors.border.onSecondary,
                    thickness = 1.dp,
                )
                OrderFoodDetail(
                    data = reservationFoodDetail,
                    isLoading = reservationFoodDetail.key in pendingFoods,
                    onReserveFoodDetailClick = {
                        onReserveFoodClick(reservationFoodDetail)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun OrderCardPreview() {
    RavanTheme {
        LazyColumn {
            orderCard(
                data = orderCardUIModelFixture1,
                dayIndex = 0,
                pendingFoods = persistentSetOf(),
                onReserveFoodClick = {}
            )
        }
    }
}
