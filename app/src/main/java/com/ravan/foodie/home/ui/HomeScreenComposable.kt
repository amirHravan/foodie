package com.ravan.foodie.home.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.home.ui.component.HomeScreen
import com.ravan.foodie.home.ui.viewmodel.HomeScreenViewModel


@Composable
fun HomeScreenComposable(
    viewModel: HomeScreenViewModel,
    navController: NavController,
    finish: () -> Unit,
) {
    BackHandler {
        finish()
    }

    HomeScreen(
        onCodeClick = { navController.navigate(FoodieRoutes.ForgetCodeScreen.route) },
        onLunchClick = { navController.navigate(FoodieRoutes.ReservableScreen.route) },
        onCalenderClick = { navController.navigate(FoodieRoutes.ReservationInfoScreen.route) },
        onProfileClick = { navController.navigate(FoodieRoutes.ProfileScreen.route) },
    )
}