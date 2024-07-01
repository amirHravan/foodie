package com.ravan.foodie.login.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.login.ui.component.body.LoginButton
import com.ravan.foodie.login.ui.component.body.LoginTextField
import com.ravan.foodie.login.ui.model.LoginButtonUIModel
import com.ravan.foodie.login.ui.model.LoginScreenUIModel
import com.ravan.foodie.login.ui.model.LoginTextFieldUIModel


@Composable
fun LoginScreen(
    data: LoginScreenUIModel,
    loginStatus: FoodieInformationBoxUIModel?,
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
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .background(RavanTheme.colors.background.primary)
                .padding(16.dp)
        ) {
            LoginTextField(
                data = LoginTextFieldUIModel(
                    placeholder = "",
                    title = stringResource(
                        id = R.string.student_number,
                    ),
                    value = data.username,
                ),
                onValueChange = onUserNameChange,
            )
            Spacer(modifier = Modifier.size(24.dp))
            LoginTextField(
                data = LoginTextFieldUIModel(
                    placeholder = "",
                    title = stringResource(
                        id = R.string.password,
                    ),
                    value = data.password,
                ),
                onValueChange = onPasswordChange,
            )
            Spacer(modifier = Modifier.size(24.dp))
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
        }
        LoginButton(
            data = LoginButtonUIModel(title = stringResource(id = R.string.login_login_button_label)),
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
        )
    }
}