package com.ravan.foodie.login.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.login.ui.component.LoginScreen
import com.ravan.foodie.login.ui.component.body.LoginButtonState
import com.ravan.foodie.login.ui.model.LoginScreenUIModel
import com.ravan.foodie.login.ui.viewmodel.LoginScreenViewModel

@Composable
fun LoginScreenComposable(
    navController: NavController,
    viewModel: LoginScreenViewModel,
    finish: () -> Unit,
) {
    val buttonState = remember(viewModel.loginToken) {
        when (viewModel.loginToken.value) {
            LoadableData.NotLoaded,
            is LoadableData.Failed,
            is LoadableData.Loaded -> LoginButtonState.Enable

            LoadableData.Loading -> LoginButtonState.Loading
        }
    }

    LoginScreen(
        data = LoginScreenUIModel(
            username = viewModel.username.value,
            password = viewModel.password.value,
        ),
        onUserNameChange = { username -> viewModel.onUserNameChange(username) },
        onPasswordChange = { password -> viewModel.onPasswordChange(password) },
        loginStatus = viewModel.informationBoxData.value,
        onLoginClick = { viewModel.onLoginClick() },
        buttonState = buttonState
    )

    LaunchedEffect(true) {
        viewModel.onLaunch()
    }

    LaunchedEffect(viewModel.loginToken.value) {
        viewModel.navHome.setNavigateAction {
            navController.navigate(FoodieRoutes.HomeScreen.route)
        }
    }

    BackHandler {
        finish()
    }


}
