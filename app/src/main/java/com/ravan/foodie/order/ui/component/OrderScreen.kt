package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.fixture.orderScreenUIModelFixture
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel
import com.ravan.foodie.order.ui.model.OrderScreenUIModel

@Composable
fun OrderScreen(
    data: OrderScreenUIModel,
    onReserveFoodClick: (OrderFoodDetailUIModel, () -> Unit) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.size(8.dp))
        }
        data.orderCardUIModelList.forEach { reserveCardUIModel ->
            orderCard(
                data = reserveCardUIModel,
                onReserveFoodClick = onReserveFoodClick,
            )
        }
    }
}

@Preview
@Composable
private fun ReserveScreenPreview() {
    RavanTheme {
        OrderScreen(
            data = orderScreenUIModelFixture,
            onReserveFoodClick = { _, _ -> },
        )
    }
}
