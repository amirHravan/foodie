package com.ravan.foodie.login.ui.component.body

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.login.ui.model.LoginTextFieldUIModel


@Composable
fun LoginTextField(
    data: LoginTextFieldUIModel,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        data.title?.let {
            Text(
                text = it,
                style = RavanTheme.typography.button,
                color = RavanTheme.colors.text.onPrimary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(8.dp))

        }
        BasicTextField(
            value = data.value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = RavanTheme.colors.text.onPrimary,
                    shape = RavanTheme.shapes.r16
                )
                .padding(16.dp),
            textStyle = RavanTheme.typography.h6.copy(
                textAlign = TextAlign.Left,
                color = RavanTheme.colors.text.onPrimary
            ),
            decorationBox = { innerTextField ->
                data.value.isEmpty().let {
                    Text(
                        text = if (data.value.isEmpty()) data.placeholder else "",
                        style = RavanTheme.typography.h6,
                        color = RavanTheme.colors.text.onPrimary.copy(alpha = 0.8f),
                        modifier = Modifier
                    )
                }
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    innerTextField()
                }
            },
            singleLine = true,
            cursorBrush = SolidColor(RavanTheme.colors.text.onPrimary),
            visualTransformation = if (isPassword) PasswordVisualTransformation(mask = '\uff65') else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
        )
    }
}

@Preview
@Composable
fun LoginTextFieldPreview() {
    RavanTheme {
        LoginTextField(
            LoginTextFieldUIModel("۴۰۰۱۰۴۹۷۵", "نام کاربری", "۴۰۰۱۰۴۹۷۵"),
//            isPassword = true,
            onValueChange = {}
        )
    }
}