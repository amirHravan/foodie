package com.ravan.foodie.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.login.ui.component.LoginScreen
import com.ravan.foodie.login.ui.model.LoginScreenUIModel
import com.ravan.foodie.login.ui.viewmodel.LoginScreenViewModel

@Composable
fun LoginScreenComposable(
    navController: NavController,
    viewModel: LoginScreenViewModel,
) {
    LoginScreen(
        data = LoginScreenUIModel(
            username = viewModel.username.value,
            password = viewModel.password.value,
        ),
        onUserNameChange = { username -> viewModel.onUserNameChange(username) },
        onPasswordChange = { password -> viewModel.onPasswordChange(password) },
        loginStatus = viewModel.informationBoxData.value,
        onLoginClick = { viewModel.onLoginClick() },
    )

    LaunchedEffect(viewModel.loginToken.value) {
        viewModel.navHome.setNavigateAction {
            navController.navigate(FoodieRoutes.HomeScreen.route)
        }
    }


}
