package com.ravan.foodie.login.ui.component.body

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.login.ui.model.LoginButtonUIModel

@Composable
fun LoginButton(
    data: LoginButtonUIModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        shape = RavanTheme.shapes.r16,
        onClick = onClick, modifier = modifier, colors = ButtonDefaults.buttonColors(
            containerColor = RavanTheme.colors.background.tertiary,
            contentColor = RavanTheme.colors.text.onTertiary,
            disabledContentColor = RavanTheme.colors.text.onTertiary,
            disabledContainerColor = RavanTheme.colors.background.tertiary.copy(alpha = 0.85f),
        )
    ) {
        Text(
            text = data.title,
            style = RavanTheme.typography.button,
            color = RavanTheme.colors.text.onTertiary,
            modifier = Modifier.padding(6.dp)
        )
    }

}

@Preview
@Composable
fun LoginButtonPreview() {
    RavanTheme {
        LoginButton(LoginButtonUIModel("ورود"), onClick = {})
    }
}