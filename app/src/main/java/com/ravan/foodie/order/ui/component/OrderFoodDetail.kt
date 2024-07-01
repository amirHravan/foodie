package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel

@Composable
fun OrderFoodDetail(
    data: OrderFoodDetailUIModel,
    onReserveFoodDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, borderColor) = getBackgroundColor(data.isSelected)

    Column(
        modifier = modifier
            .clickable(enabled = !data.isDisable, onClick = onReserveFoodDetailClick)
            .background(if (data.isDisable) backgroundColor.copy(alpha = 0.5f) else backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data.foodName,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 4.dp)
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_dollor,
                text = data.price.toString().toLocalNumber(),
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        if (data.isDisable) {
            FoodieTextIconRow(
                data = FoodieTextIconRowUIModel(
                    iconRes = R.drawable.ic_close,
                    text = stringResource(R.string.order_order_food_detail_disabled),
                ),
                color = RavanTheme.colors.text.onSecondary,
                textStyle = RavanTheme.typography.body2,
            )
        }
    }
}

@Composable
private fun getBackgroundColor(isSelected: Boolean): Pair<Color, Color> {
    return if (isSelected) {
        RavanTheme.colors.background.success to RavanTheme.colors.border.onSuccess
    } else {
        RavanTheme.colors.background.secondary to Color.Transparent
    }
}

@Preview
@Composable
private fun ReserveFoodDetailPreview() {
    RavanTheme {
        Column(
            modifier = Modifier
        ) {
            OrderFoodDetail(
                modifier = Modifier,
                data = OrderFoodDetailUIModel(
                    foodName = "کوفت",
                    programId = 0,
                    selfId = 1,
                    mealTypeId = 1,
                    foodTypeId = 1,
                    price = 8000,
                    isSelected = false
                ),
                onReserveFoodDetailClick = {}
            )
        }

    }
}
