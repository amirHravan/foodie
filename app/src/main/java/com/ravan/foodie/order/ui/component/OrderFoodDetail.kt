package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel

@Composable
fun OrderFoodDetail(
    data: OrderFoodDetailUIModel,
    onReserveFoodDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
    showActionButton: Boolean = true
) {
    val (backgroundColor, _) = getBackgroundColor(data.isSelected, data.isDisable, data.canSelect)
    val isFoodMissed = remember { mutableStateOf(!data.isSelected && data.isDisable) }
    val tagLabelId =
        remember {
            mutableIntStateOf(
                getTagLabel(
                    isFoodMissed.value,
                    data.isSelected,
                    data.canSelect
                )
            )
        }
    val buttonData = remember { mutableStateOf(getButtonTitleIconId(data.isSelected)) }

    Column(
        modifier = modifier
            .background(RavanTheme.colors.background.secondary)
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data.foodName,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6,
            textDecoration = null,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_money_bag,
                text = data.price.toString().toLocalNumber(),
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
            textDecoration = null,
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_tag,
                text = stringResource(tagLabelId.intValue),
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        if (!data.isDisable && data.canSelect && showActionButton) {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = buttonData.value.second,
                    title = stringResource(id = buttonData.value.first),
                ), onClick = onReserveFoodDetailClick,
                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
            )
        }
    }
}

fun getButtonTitleIconId(selected: Boolean): Pair<Int, Int> {
    return if (selected) {
        R.string.order_order_food_detail_cancel to R.drawable.ic_remove
    } else {
        R.string.order_order_food_detail_reserve to R.drawable.ic_add
    }
}

fun getTagLabel(isMissed: Boolean, isSelected: Boolean, canSelect: Boolean): Int {
    return if (isMissed) {
        R.string.order_order_food_detail_disabled
    } else if (isSelected) {
        R.string.order_order_food_detail_selected
    } else if (!canSelect) {
        R.string.order_order_food_detail_selected_elsewhere
    } else {
        R.string.order_order_food_detail_not_selected
    }
}


@Composable
private fun getBackgroundColor(
    isSelected: Boolean,
    isDisabled: Boolean,
    canSelect: Boolean
): Pair<Color, Color> {
    return if (isSelected) {
        RavanTheme.colors.background.success to RavanTheme.colors.border.onSuccess
    } else if (isDisabled) {
        RavanTheme.colors.background.disable.copy(alpha = 0.1f) to RavanTheme.colors.border.onDisable
    } else if (!canSelect) {
        RavanTheme.colors.background.alreadySelectedElsewhere to RavanTheme.colors.border.onSuccess
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
                modifier = Modifier.fillMaxWidth(),
                data = OrderFoodDetailUIModel(
                    foodName = "کوفت",
                    programId = 0,
                    selfId = 1,
                    mealTypeId = 1,
                    foodTypeId = 1,
                    price = 8000,
                    isSelected = false,
                    isDisable = true,
                ),
                onReserveFoodDetailClick = {}
            )
        }

    }
}
