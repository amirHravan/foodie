package com.ravan.foodie.forget.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.forget.ui.fixture.forgetCodeDetailRow2
import com.ravan.foodie.forget.ui.model.ForgetCodeFoodDetailUIModel

@Composable
fun ForgetCodeFoodDetailRow(
    data: ForgetCodeFoodDetailUIModel,
    onGetForgetCodeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        Text(
            text = data.foodName,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6,
            modifier = Modifier
                .padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_location,
                text = data.selfName,
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        Spacer(modifier = Modifier.size(8.dp))
        AnimatedVisibility(visible = data.forgetCode != null) {
            FoodieTextIconRow(
                data = FoodieTextIconRowUIModel(
                    iconRes = R.drawable.ic_code,
                    text = data.forgetCode?.toLocalNumber() ?: "کد فراموشی نداریم!",
                ),
                color = RavanTheme.colors.text.onSecondary,
                textStyle = RavanTheme.typography.body2,
            )
        }
        AnimatedVisibility(visible = data.forgetCode == null) {
            FoodieButton(
                data = FoodieButtonUIModel.General(title = "دریافت کد فراموشی", iconRes = null),
                onClick = onGetForgetCodeClick,
                shape = RavanTheme.shapes.r8,

            )

        }

    }
}

@Preview
@Composable
private fun ForgetCodeFoodDetailRowPreview() {
    RavanTheme {
        ForgetCodeFoodDetailRow(
            data = forgetCodeDetailRow2,
            onGetForgetCodeClick = {}
        )
    }
}
