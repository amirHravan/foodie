package com.ravan.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ravan.foodie.autoreserve.ui.AutoReserveComposable
import com.ravan.foodie.autoreserve.ui.PrioritySelectionComposable
import com.ravan.foodie.autoreserve.ui.viewmodel.AutoReserveViewModel
import com.ravan.foodie.autoreserve.ui.viewmodel.PrioritySelectionViewModel
import com.ravan.foodie.dailysell.ui.DailySellComposable
import com.ravan.foodie.dailysell.ui.viewmodel.DailySellViewModel
import com.ravan.foodie.domain.notification.createNotificationChannel
import com.ravan.foodie.domain.notification.setAlarmsBasedOnPreference
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.home.ui.component.BottomNavigationBar
import com.ravan.foodie.login.ui.LoginScreenComposable
import com.ravan.foodie.login.ui.viewmodel.LoginScreenViewModel
import com.ravan.foodie.order.ui.OrderScreenComposable
import com.ravan.foodie.order.ui.viewmodel.OrderScreenViewModel
import com.ravan.foodie.profile.ui.ProfileComposable
import com.ravan.foodie.profile.ui.viewmodel.ProfileViewModel
import com.ravan.foodie.reserveinfo.ui.ReservationInfoScreenComposable
import com.ravan.foodie.reserveinfo.ui.viewmodel.ReservationInfoViewModel
import com.ravan.foodie.settings.ui.SettingsComposable
import com.ravan.foodie.settings.ui.viewmodel.SettingsViewModel
import com.ravan.foodie.splash.ui.SplashScreenComposable
import com.ravan.foodie.splash.ui.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RavanTheme {
                val navController = rememberNavController()
                Surface(
                    color = RavanTheme.colors.background.primary,
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = navController) },
                        containerColor = RavanTheme.colors.background.primary,
                    ) { paddingValues ->

                        val bottomPadding = remember(paddingValues) {
                            paddingValues.calculateBottomPadding()
                        }

                        NavHost(
                            navController = navController,
                            startDestination = FoodieRoutes.SplashScreen.route,
                            enterTransition = { fadeIn(animationSpec = tween(700)) },
                            exitTransition = { fadeOut(animationSpec = tween(700)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(700)) },
                            popExitTransition = { fadeOut(animationSpec = tween(700)) },
                            modifier = Modifier
                                .padding(bottom = bottomPadding)
                        ) {
                            composable(FoodieRoutes.SplashScreen.route) {
                                val splashScreenViewModel = getViewModel<SplashScreenViewModel>();
                                SplashScreenComposable(
                                    viewModel = splashScreenViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(FoodieRoutes.LoginScreen.route) {
                                val loginViewModel = getViewModel<LoginScreenViewModel>();
                                LoginScreenComposable(
                                    viewModel = loginViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(FoodieRoutes.ReservationInfoScreen.route) {
                                val reservationInfoViewModel =
                                    getViewModel<ReservationInfoViewModel>();
                                ReservationInfoScreenComposable(
                                    viewModel = reservationInfoViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(FoodieRoutes.ReservableScreen.route) {
                                val reservationInfoViewModel = getViewModel<OrderScreenViewModel>();
                                OrderScreenComposable(
                                    viewModel = reservationInfoViewModel,
                                    navController = navController
                                )
                            }
                            composable(FoodieRoutes.ProfileScreen.route) {
                                val reservationInfoViewModel = getViewModel<ProfileViewModel>();
                                ProfileComposable(
                                    viewModel = reservationInfoViewModel,
                                    navController = navController
                                )
                            }
                            composable(FoodieRoutes.DailySaleScreen.route) {
                                val dailySellViewModel = getViewModel<DailySellViewModel>();
                                DailySellComposable(
                                    viewModel = dailySellViewModel,
                                    navController = navController
                                )
                            }
                            composable(FoodieRoutes.SettingsScreen.route) {
                                val settingsViewModel = getViewModel<SettingsViewModel>();
                                SettingsComposable(
                                    viewModel = settingsViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(FoodieRoutes.AutomaticReservationScreen.route) {
                                val autoReserveViewModel = getViewModel<AutoReserveViewModel>();
                                AutoReserveComposable(
                                    viewModel = autoReserveViewModel,
                                    navController = navController
                                )
                            }
                            composable(FoodieRoutes.FoodPriorityScreen.route) {
                                val prioritySelectionViewModel =
                                    getViewModel<PrioritySelectionViewModel>();
                                PrioritySelectionComposable(
                                    viewModel = prioritySelectionViewModel,
                                    navController = navController
                                )
                            }

                        }
                    }
                }
            }
        }

        createNotificationChannel(this)
        setAlarmsBasedOnPreference(this)
    }


}