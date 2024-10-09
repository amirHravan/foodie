package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
            .background(RavanTheme.colors.background.secondary)
            .background(backgroundColor)
            .border(2.dp, borderColor, RavanTheme.shapes.r8)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = data.message,
            style = RavanTheme.typography.body2,
            color = RavanTheme.colors.text.onSecondary,
            modifier = Modifier
                .padding(6.dp),
        )
    }

}

@Composable
fun FoodieInformationBoxState.getBackgroundColor(): Color {
    return when (this) {
        FoodieInformationBoxState.SUCCESS -> RavanTheme.colors.background.success
        FoodieInformationBoxState.FAILED -> RavanTheme.colors.background.fail
    }
//    return RavanTheme.colors.background.secondary.copy(alpha = 0.9f)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(RavanTheme.colors.background.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            FoodieInformationBox(
                data = FoodieInformationBoxUIModel(
                    state = FoodieInformationBoxState.SUCCESS,
                    message = "ورود موفقیت آمیز بود."
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

        }
    }
}