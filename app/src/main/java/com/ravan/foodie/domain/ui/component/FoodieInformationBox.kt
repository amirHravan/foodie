package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodieInformationBox(
    data: FoodieInformationBoxUIModel,
    modifier: Modifier = Modifier
) {
    val backgroundColor = data.state.getBackgroundColor()
    val borderColor = data.state.getBorderColor()

    Box(
        modifier = modifier
            .clip(RavanTheme.shapes.r8)
            .background(backgroundColor)
            .border(1.dp, borderColor, RavanTheme.shapes.r8)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = data.message,
            style = RavanTheme.typography.body2,
            color = RavanTheme.colors.text.onPrimary,
            modifier = Modifier
                .padding(8.dp)
                .offset(y = (-3).dp),
        )
    }

}

@Composable
fun FoodieInformationBoxState.getBackgroundColor(): Color {
    return when (this) {
        FoodieInformationBoxState.SUCCESS -> RavanTheme.colors.background.success
        FoodieInformationBoxState.FAILED -> RavanTheme.colors.background.fail
    }
}


@Composable
fun FoodieInformationBoxState.getBorderColor(): Color {
    return when (this) {
        FoodieInformationBoxState.SUCCESS -> RavanTheme.colors.border.onSuccess
        FoodieInformationBoxState.FAILED -> RavanTheme.colors.border.onFail
    }
}

@Preview
@Composable
fun LoginInformationBoxPreview() {
    RavanTheme {
        FoodieInformationBox(
            data = FoodieInformationBoxUIModel(
                state = FoodieInformationBoxState.SUCCESS,
                message = "ورود موفقیت آمیز بود."
            )
        )
    }
}