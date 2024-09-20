package com.ravan.foodie.login.ui.component.body

import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme


@Composable
fun LoginDivider(
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.border.onPrimary,
) {
    Divider(
        thickness = 2.dp,
        color = DividerDefaults.color.copy(
            alpha = 0.8f,
            red = color.red,
            green = color.green,
            blue = color.blue
        ),
        modifier = modifier,
    )
}

@Preview
@Composable
fun LoginDividerPreview() {
    RavanTheme {
        LoginDivider()
    }
}