package com.ravan.foodie.login.ui.component.body

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.login.ui.model.LoginButtonUIModel

@Composable
fun LoginButton(
    data: LoginButtonUIModel,
    state: LoginButtonState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val enabled = remember(state) {
        state.isButtonEnable()
    }
    Button(
        shape = RavanTheme.shapes.r16,
        onClick = onClick,
        modifier = modifier, colors = ButtonDefaults.buttonColors(
            containerColor = RavanTheme.colors.background.tertiary,
            contentColor = RavanTheme.colors.text.onTertiary,
            disabledContentColor = RavanTheme.colors.text.onTertiary,
            disabledContainerColor = RavanTheme.colors.background.tertiary.copy(alpha = 0.85f),
        ),
        enabled = enabled
    ) {
        when (state) {
            LoginButtonState.Loading -> {
                FoodieProgressIndicator()
            }

            LoginButtonState.Enable,
            LoginButtonState.Disabled -> {
                Text(
                    text = data.title,
                    style = RavanTheme.typography.button,
                    color = RavanTheme.colors.text.onTertiary,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }

}

@Preview
@Composable
fun LoginButtonPreview() {
    RavanTheme {
        LoginButton(LoginButtonUIModel("ورود"), onClick = {}, state = LoginButtonState.Loading)
    }
}