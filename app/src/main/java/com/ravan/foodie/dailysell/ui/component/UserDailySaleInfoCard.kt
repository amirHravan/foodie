package com.ravan.foodie.dailysell.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.dailysell.ui.model.UserDailySaleCardUIModel
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieButtonState
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber

@Composable
fun UserDailySaleCard(
    uiModel: UserDailySaleCardUIModel,
    onGetForgetCode: (Int, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {

    val buttonState = remember(uiModel) {
        mutableStateOf(FoodieButtonState.Enabled)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnimatedVisibility(visible = uiModel.forgetCode != null) {
            FoodieTextIconRow(
                data = FoodieTextIconRowUIModel(
                    iconRes = R.drawable.ic_code,
                    text = uiModel.forgetCode?.toLocalNumber() ?: "کد فراموشی نداریم!",
                ),
                color = RavanTheme.colors.text.onSecondary,
                textStyle = RavanTheme.typography.body2,
            )
        }
        AnimatedVisibility(visible = uiModel.forgetCode == null && !uiModel.consumed) {
            FoodieButton(
                data = FoodieButtonUIModel.General(title = "دریافت کد فراموشی", iconRes = null),
                onClick = {
                    buttonState.value = FoodieButtonState.Loading
                    onGetForgetCode(uiModel.id) {
                        buttonState.value = FoodieButtonState.Enabled
                    }
                },
                shape = RavanTheme.shapes.r16,
                state = buttonState.value
            )
        }
    }
}

@Preview
@Composable
private fun UserDailySaleCardPreview() {
    RavanTheme {
        UserDailySaleCard(
            uiModel = UserDailySaleCardUIModel(
                consumed = false,
                id = 1,
                forgetCode = null,

                ),
            onGetForgetCode = { _, _ -> }
        )
    }

}