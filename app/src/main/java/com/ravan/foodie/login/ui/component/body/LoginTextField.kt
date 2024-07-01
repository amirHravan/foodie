package com.ravan.foodie.login.ui.component.body

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.login.ui.model.LoginTextFieldUIModel


@Composable
fun LoginTextField(
    data: LoginTextFieldUIModel,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = data.title,
            style = RavanTheme.typography.button,
            color = RavanTheme.colors.text.onPrimary,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.size(8.dp))
        BasicTextField(
            value = data.value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            textStyle = RavanTheme.typography.h6.copy(
                textAlign = TextAlign.Left,
                color = RavanTheme.colors.text.onPrimary
            ),
            decorationBox = { innerTextField ->
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    data.value.isEmpty().let {
                        Text(
                            text = if (data.value.isEmpty()) data.placeholder else "",
                            style = RavanTheme.typography.h6,
                            color = RavanTheme.colors.text.onPrimary.copy(alpha = 0.5f),
                            modifier = Modifier
                        )
                    }
                    innerTextField()
                }
            },
            cursorBrush = Brush.verticalGradient(
                colors = listOf(
                    RavanTheme.colors.text.onPrimary,
                    RavanTheme.colors.text.onPrimary.copy(alpha = 0.5f)
                )
            )
        )
        LoginDivider()
    }
}

@Preview
@Composable
fun LoginTextFieldPreview() {
    RavanTheme {
        LoginTextField(
            LoginTextFieldUIModel("۴۰۰۱۰۴۹۷۵", "نام کاربری", "۴۰۰۱۰۴۹۷۵"),
            onValueChange = {})
    }
}