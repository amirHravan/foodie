package com.ravan.foodie.domain.ui.component

import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme


@Composable
fun FoodieDivider(
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.border.onPrimary,
    alpha: Float = 0.8f,
    thickness: Int = 2,
) {
    Divider(
        thickness = thickness.dp,
        color = DividerDefaults.color.copy(
            alpha = alpha,
            red = color.red,
            green = color.green,
            blue = color.blue
        ),
        modifier = modifier,
    )
}

@Preview
@Composable
fun FoodieDividerPreview() {
    RavanTheme {
        FoodieDivider()
    }
}