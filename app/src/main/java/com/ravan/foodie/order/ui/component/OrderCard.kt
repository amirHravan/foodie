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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.ui.fixture.orderCardUIModelFixture1
import com.ravan.foodie.order.ui.model.OrderCardUIModel
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel


@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.orderCard(
    data: OrderCardUIModel,
    onReserveFoodClick: (OrderFoodDetailUIModel, () -> Unit) -> Unit,
) {
    if (data.reserveInfoList.isNotEmpty()) {
        stickyHeader {
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
                thickness = 2,
            )
        }
        itemsIndexed(data.reserveInfoList.toList()) { _, (mealType, reservationFoodDetailList) ->
            OrderMealTypeSection(
                mealType,
                reservationFoodDetailList,
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
    onReserveFoodClick: (OrderFoodDetailUIModel, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLoading = remember(mealType) {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .background(RavanTheme.colors.background.primary)
            .padding(8.dp)
            .clip(RavanTheme.shapes.r8)
            .background(RavanTheme.colors.background.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading.value) {
            FoodieProgressIndicator(
                color = RavanTheme.colors.text.onSecondary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        } else if (reservationFoodDetailList.isNotEmpty()) {

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
                    thickness = 1,
                )
                OrderFoodDetail(
                    data = reservationFoodDetail,
                    onReserveFoodDetailClick = {
                        isLoading.value = true
                        onReserveFoodClick(
                            reservationFoodDetail
                        ) { isLoading.value = false }
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
                onReserveFoodClick = { _, _ -> }
            )
        }
    }
}
