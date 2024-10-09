package com.ravan.foodie.dailysell.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.dailysell.ui.model.DailySaleCardUIModel
import com.ravan.foodie.dailysell.ui.model.UserDailySaleCardUIModel
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieButtonState
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber

@Composable
fun DailySaleCard(
    uiModel: DailySaleCardUIModel,
    onOrderDailySaleClick: (Int, () -> Unit) -> Unit,
    onGetForgetCodeClick: (Int, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {

    val buttonState = remember(uiModel) {
        mutableStateOf(FoodieButtonState.Enabled)
    }

    val backgroundColor = uiModel.userDailySaleInfo?.let {
        if (it.consumed) {
            RavanTheme.colors.background.disable
        } else {
            RavanTheme.colors.background.success
        }
    } ?: RavanTheme.colors.background.secondary

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RavanTheme.shapes.r8)
            .background(color = backgroundColor)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = uiModel.foodName,
            style = RavanTheme.typography.h4,
            modifier = Modifier
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            uiModel.startTime?.let {
                Text(
                    text = "$it - ${uiModel.finishTime ?: ""}",
                    style = RavanTheme.typography.body2,
                    modifier = Modifier
                )
            }
            Text(
                text = "${uiModel.soldCount} از ${uiModel.count} فروخته شده است.",
                style = RavanTheme.typography.body2,
                modifier = Modifier
            )
        }
        FoodieDivider(
            color = RavanTheme.colors.border.onSecondary,
            thickness = 1.dp,
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_clock,
                text = uiModel.mealTypeName,
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_dollor,
                text = uiModel.price.toLocalNumber(),
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        uiModel.selfName?.let {
            FoodieTextIconRow(
                data = FoodieTextIconRowUIModel(
                    iconRes = R.drawable.ic_location,
                    text = uiModel.selfName,
                ),
                color = RavanTheme.colors.text.onSecondary,
                textStyle = RavanTheme.typography.body2,
            )

        }
        AnimatedVisibility(visible = uiModel.showOrderButton) {
            FoodieButton(
                data = FoodieButtonUIModel.General(title = "سفارش غذا", iconRes = null),
                onClick = {
                    buttonState.value = FoodieButtonState.Loading
                    onOrderDailySaleClick(uiModel.id) {
                        buttonState.value = FoodieButtonState.Enabled
                    }
                },
                shape = RavanTheme.shapes.r16,
                state = buttonState.value,
            )
        }

        uiModel.userDailySaleInfo?.let {
            FoodieTextIconRow(
                data = FoodieTextIconRowUIModel(
                    iconRes = R.drawable.ic_check,
                    text = if (uiModel.userDailySaleInfo.consumed) "مصرف شده" else "رزرو شده",
                ),
                color = RavanTheme.colors.text.onSecondary,
                textStyle = RavanTheme.typography.body2,
            )
            UserDailySaleCard(
                uiModel = it,
                onGetForgetCode = onGetForgetCodeClick
            )
        }
    }


}

@Preview
@Composable
private fun DailySaleCardPreview() {
    RavanTheme {
        Column(
            modifier = Modifier
        ) {
            DailySaleCard(
                uiModel = DailySaleCardUIModel(
                    count = "10".toLocalNumber(),
                    soldCount = "5".toLocalNumber(),
                    foodName = "پیتزا",
                    mealTypeName = "نهار",
                    price = "10000",
                    startTime = "12:00".toLocalNumber(),
                    finishTime = "14:00".toLocalNumber(),
                    showOrderButton = true,
                    id = 0,
                    selfName = "رستوران",
                    userDailySaleInfo = null,
                ),
                onOrderDailySaleClick = { _, _ -> },
                onGetForgetCodeClick = { _, _ -> }
            )

            DailySaleCard(
                uiModel = DailySaleCardUIModel(
                    count = "10".toLocalNumber(),
                    soldCount = "5".toLocalNumber(),
                    foodName = "پیتزا",
                    mealTypeName = "نهار",
                    price = "10000",
                    startTime = "12:00".toLocalNumber(),
                    finishTime = "14:00".toLocalNumber(),
                    showOrderButton = false,
                    id = 0,
                    selfName = "رستوران",
                    userDailySaleInfo = UserDailySaleCardUIModel(
                        consumed = false,
                        id = 1,
                        forgetCode = null,
                    ),
                ),
                onOrderDailySaleClick = { _, _ -> },
                onGetForgetCodeClick = { _, _ -> }
            )
        }
    }

}