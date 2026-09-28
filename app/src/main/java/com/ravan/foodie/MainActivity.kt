package com.ravan.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
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
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ravan.foodie.account.ui.component.AccountSwitcherSheet
import com.ravan.foodie.account.ui.component.RemoveAccountDialog
import com.ravan.foodie.account.ui.model.AccountNavEvent
import com.ravan.foodie.account.ui.viewmodel.AccountsViewModel
import com.ravan.foodie.domain.util.LOGIN_ARG_MODE
import com.ravan.foodie.domain.util.LOGIN_ARG_USERNAME
import com.ravan.foodie.domain.util.navigateClearingStack
import com.ravan.foodie.login.domain.model.LoginMode
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RavanTheme {
                val navController = rememberNavController()
                // Activity-scoped so Profile and the long-press switcher share it.
                val accountsViewModel = getViewModel<AccountsViewModel>()
                val screenStoreOwner = accountsViewModel.screenViewModelStoreOwner.value
                AccountNavigation(accountsViewModel, navController)
                Surface(
                    color = RavanTheme.colors.background.primary,
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                onProfileLongPress = { accountsViewModel.onProfileLongPress() },
                            )
                        },
                        containerColor = RavanTheme.colors.background.primary,
                    ) { paddingValues ->

                        val bottomPadding = remember(paddingValues.calculateBottomPadding()) {
                            mutableStateOf(paddingValues.calculateBottomPadding())
                        }

                        NavHost(
                            navController = navController,
                            startDestination = FoodieRoutes.SplashScreen.route,
                            enterTransition = { fadeIn(animationSpec = tween(700)) },
                            exitTransition = { fadeOut(animationSpec = tween(700)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(700)) },
                            popExitTransition = { fadeOut(animationSpec = tween(700)) },
                            modifier = Modifier
                                .padding(bottom = bottomPadding.value)
                        ) {
                            composable(route = FoodieRoutes.SplashScreen.route) {
                                val splashScreenViewModel = koinViewModel<SplashScreenViewModel>()
                                SplashScreenComposable(
                                    viewModel = splashScreenViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(
                                route = FoodieRoutes.LoginScreen.route,
                                arguments = listOf(
                                    navArgument(LOGIN_ARG_MODE) {
                                        type = NavType.StringType
                                        defaultValue = LoginMode.Initial.name
                                    },
                                    navArgument(LOGIN_ARG_USERNAME) {
                                        type = NavType.StringType
                                        nullable = true
                                        defaultValue = null
                                    },
                                ),
                            ) { entry ->
                                val mode = entry.arguments?.getString(LOGIN_ARG_MODE)
                                    ?.let { runCatching { LoginMode.valueOf(it) }.getOrNull() }
                                    ?: LoginMode.Initial
                                val username = entry.arguments?.getString(LOGIN_ARG_USERNAME)
                                val loginViewModel = koinViewModel<LoginScreenViewModel> {
                                    parametersOf(mode, username)
                                }
                                LoginScreenComposable(
                                    viewModel = loginViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(
                                route = FoodieRoutes.ReservationInfoScreen.route,
//                                popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//                                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                            ) {
                                val reservationInfoViewModel =
                                    koinViewModel<ReservationInfoViewModel>(
                                        viewModelStoreOwner = screenStoreOwner
                                    )
                                ReservationInfoScreenComposable(
                                    viewModel = reservationInfoViewModel,
                                    navController = navController,
                                    finish = { finish() }
                                )
                            }
                            composable(
                                route = FoodieRoutes.ReservableScreen.route,
//                                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
//                                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                                popEnterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Start,
                                        tween(700)
                                    )
                                },
                                popExitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Start,
                                        tween(700)
                                    )
                                },
                            ) {
                                val orderScreenViewModel = koinViewModel<OrderScreenViewModel>(
                                    viewModelStoreOwner = screenStoreOwner
                                )
                                OrderScreenComposable(
                                    viewModel = orderScreenViewModel,
                                    navController = navController
                                )
                            }
                            composable(
                                route = FoodieRoutes.ProfileScreen.route,
//                                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//                                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                                popEnterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.End,
                                        tween(700)
                                    )
                                },
                                popExitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.End,
                                        tween(700)
                                    )
                                },
                            ) {
                                val profileViewModel = koinViewModel<ProfileViewModel>(
                                    viewModelStoreOwner = screenStoreOwner
                                )
                                ProfileComposable(
                                    viewModel = profileViewModel,
                                    accountsViewModel = accountsViewModel,
                                    navController = navController,
                                )
                            }
                            composable(
                                route = FoodieRoutes.DailySaleScreen.route,
//                                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
//                                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                                popEnterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.End,
                                        tween(700)
                                    )
                                },
                                popExitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.End,
                                        tween(700)
                                    )
                                },
                            ) {
                                val dailySellViewModel = koinViewModel<DailySellViewModel>(
                                    viewModelStoreOwner = screenStoreOwner
                                )
                                DailySellComposable(
                                    viewModel = dailySellViewModel,
                                    navController = navController
                                )
                            }
                            composable(route = FoodieRoutes.SettingsScreen.route) {
                                val settingsViewModel = koinViewModel<SettingsViewModel>(
                                    viewModelStoreOwner = screenStoreOwner
                                )
                                SettingsComposable(
                                    viewModel = settingsViewModel,
                                    navController = navController,
                                )
                            }
                            composable(
                                route = FoodieRoutes.AutomaticReservationScreen.route,
//                                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
//                                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                                popEnterTransition = {
                                    slideIntoContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Start,
                                        tween(700)
                                    )
                                },
                                popExitTransition = {
                                    slideOutOfContainer(
                                        AnimatedContentTransitionScope.SlideDirection.Start,
                                        tween(700)
                                    )
                                },
                            ) {
                                val autoReserveViewModel = koinViewModel<AutoReserveViewModel>(
                                    viewModelStoreOwner = screenStoreOwner
                                )
                                AutoReserveComposable(
                                    viewModel = autoReserveViewModel,
                                    navController = navController
                                )
                            }
                            composable(route = FoodieRoutes.FoodPriorityScreen.route) {
                                val prioritySelectionViewModel =
                                    koinViewModel<PrioritySelectionViewModel>(
                                        viewModelStoreOwner = screenStoreOwner
                                    )
                                PrioritySelectionComposable(
                                    viewModel = prioritySelectionViewModel,
                                    navController = navController
                                )
                            }

                        }
                    }
                }
                AccountDialogs(accountsViewModel)
            }
        }

        createNotificationChannel(this)
        setAlarmsBasedOnPreference(this)
    }
}

/** Follows the account view model's navigation requests (after a switch, add or removal). */
@Composable
private fun AccountNavigation(
    accountsViewModel: AccountsViewModel,
    navController: NavController,
) {
    LaunchedEffect(accountsViewModel) {
        accountsViewModel.navEvents.collect { event ->
            when (event) {
                AccountNavEvent.Home ->
                    navController.navigateClearingStack(FoodieRoutes.ReservationInfoScreen.route)

                is AccountNavEvent.Login -> {
                    val route = FoodieRoutes.login(event.mode, event.username)
                    if (event.clearStack) {
                        navController.navigateClearingStack(route)
                    } else {
                        navController.navigate(route)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountDialogs(accountsViewModel: AccountsViewModel) {
    val reservesInFlight by accountsViewModel.reservesInFlight.collectAsState()

    if (accountsViewModel.showSwitcher.value) {
        ModalBottomSheet(
            onDismissRequest = { accountsViewModel.onSwitcherDismiss() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = RavanTheme.colors.background.secondary,
        ) {
            // The sheet is its own window and loses the theme's right-to-left layout.
            RavanTheme {
                AccountSwitcherSheet(
                    accounts = accountsViewModel.accounts.value,
                    canAddAccount = accountsViewModel.canAddAccount.value,
                    switchingTo = accountsViewModel.switchingTo.value,
                    waitingForReserve = reservesInFlight > 0,
                    status = accountsViewModel.status.value,
                    onAccountClick = { accountsViewModel.onAccountClick(it) },
                    onLoginAgainClick = { accountsViewModel.onLoginAgainClick(it) },
                    onAddAccountClick = { accountsViewModel.onAddAccountClick() },
                )
            }
        }
    }

    accountsViewModel.pendingRemoval.value?.let {
        RemoveAccountDialog(
            account = it,
            onConfirm = { accountsViewModel.onRemoveConfirm() },
            onDismiss = { accountsViewModel.onRemoveDismiss() },
        )
    }
}
