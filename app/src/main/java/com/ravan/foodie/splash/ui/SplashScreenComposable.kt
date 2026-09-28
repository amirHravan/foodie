package com.ravan.foodie.splash.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.domain.util.navigateClearingStack
import com.ravan.foodie.login.domain.model.LoginMode
import com.ravan.foodie.splash.ui.component.SplashScreen
import com.ravan.foodie.splash.ui.viewmodel.SplashScreenViewModel

@Composable
fun SplashScreenComposable(
    viewModel: SplashScreenViewModel,
    navController: NavController,
    finish: () -> Unit,
) {
    val context = LocalContext.current
    val networkErrorState = remember(
        viewModel.showNetworkError.value
    ) {
        viewModel.showNetworkError.value
    }
    SplashScreen(
        shouldShowNetworkError = networkErrorState,
        onReload = { viewModel.onReload(context) },
    )

    BackHandler {
        finish()
    }

    LaunchedEffect(true) {
        viewModel.navLogin.setNavigateAction {
            navController.navigateClearingStack(FoodieRoutes.login(LoginMode.Initial))
        }
        viewModel.navReauth.setNavigateAction {
            navController.navigateClearingStack(
                FoodieRoutes.login(LoginMode.Reauth, viewModel.reauthUsername)
            )
        }
        viewModel.navReserveInfo.setNavigateAction {
            navController.navigateClearingStack(FoodieRoutes.ReservationInfoScreen.route)
        }

        viewModel.onLaunch(context)
    }


}