package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.fixture.orderCardUIModelFixture1
import com.ravan.foodie.order.ui.fixture.orderCardUIModelFixture2
import com.ravan.foodie.order.ui.fixture.orderScreenUIModelFixture
import com.ravan.foodie.order.ui.model.OrderScreenUIModel
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel

@Composable
fun OrderScreen(
    data: OrderScreenUIModel,
    onReserveFoodClick: (OrderFoodDetailUIModel) -> Unit,
    shouldLoadMore: Boolean,
    onLoadNextWeek: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        data.orderCardUIModelList.forEach { reserveCardUIModel ->
            orderCard(
                data = reserveCardUIModel,
                onReserveFoodClick = onReserveFoodClick,
            )
        }
        if (shouldLoadMore) {
            item {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { onLoadNextWeek() }
                    .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FoodieProgressIndicator()
                }

            }
        }
    }
}

@Preview
@Composable
private fun ReserveScreenPreview() {
    RavanTheme {
        OrderScreen(
            data = orderScreenUIModelFixture,
            onReserveFoodClick = {},
            onLoadNextWeek = {},
            shouldLoadMore = false
        )
    }
}
