package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodieProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.text.onPrimary,
    strokeWidth: Dp = 5.dp
) {
    Box(modifier = modifier, contentAlignment = Center) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = strokeWidth,
            modifier = Modifier,
        )
    }

}

@Preview
@Composable
fun FoodieProgressIndicatorPreview() {
    RavanTheme {
        FoodieProgressIndicator()
    }
}
