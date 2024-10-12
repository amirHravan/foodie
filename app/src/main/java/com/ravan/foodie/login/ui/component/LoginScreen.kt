package com.ravan.foodie.login.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieButtonState
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.login.ui.component.body.LoginButton
import com.ravan.foodie.login.ui.component.body.LoginButtonState
import com.ravan.foodie.login.ui.component.body.LoginTextField
import com.ravan.foodie.login.ui.model.LoginButtonUIModel
import com.ravan.foodie.login.ui.model.LoginScreenUIModel
import com.ravan.foodie.login.ui.model.LoginTextFieldUIModel


@Composable
fun LoginScreen(
    data: LoginScreenUIModel,
    loginStatus: FoodieInformationBoxUIModel?,
    buttonState: FoodieButtonState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = RavanTheme.typography.h4,
            color = RavanTheme.colors.text.onPrimary,
        )
        Spacer(modifier = Modifier.size(24.dp))
        LoginTextField(
            data = LoginTextFieldUIModel(
                placeholder = stringResource(
                    id = R.string.student_number,
                ),
                title = null,
                value = data.username,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            onValueChange = onUserNameChange,
        )
        Spacer(modifier = Modifier.size(16.dp))
        LoginTextField(
            data = LoginTextFieldUIModel(
                placeholder = stringResource(
                    id = R.string.password,
                ),
                title = null,
                value = data.password,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                autoCorrect = false,
            ),
            isPassword = true,
            onValueChange = onPasswordChange,
        )
        Spacer(modifier = Modifier.size(8.dp))
        AnimatedVisibility(visible = loginStatus != null) {
            val status = remember {
                loginStatus
            }
            status?.let {
                FoodieInformationBox(
                    data = it,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        FoodieButton(
            data = FoodieButtonUIModel.General(
                title = stringResource(id = R.string.login_login_button_label),
                iconRes = R.drawable.ic_login,
            ), onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            state = buttonState,
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    RavanTheme {
        LoginScreen(
            data = LoginScreenUIModel("", ""),
            loginStatus = null,
            onUserNameChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            buttonState = FoodieButtonState.Enabled
        )
    }
}