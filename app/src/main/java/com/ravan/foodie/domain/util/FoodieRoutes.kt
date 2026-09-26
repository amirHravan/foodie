package com.ravan.foodie.domain.util

import android.net.Uri
import androidx.navigation.NavController
import com.ravan.foodie.login.domain.model.LoginMode

enum class FoodieRoutes(
    val route: String,
) {
    LoginScreen("login?$LOGIN_ARG_MODE={$LOGIN_ARG_MODE}&$LOGIN_ARG_USERNAME={$LOGIN_ARG_USERNAME}"),
    SplashScreen("splash_screen"),
    ReservationInfoScreen("reservation_info"),
    ReservableScreen("reservable"),
    ProfileScreen("profile"),
    DailySaleScreen("daily_sale"),
    AutomaticReservationScreen("automatic_reservation"),
    SettingsScreen("settings"),
    FoodPriorityScreen("food_priority"),
    ;

    companion object {
        fun login(mode: LoginMode, username: String? = null): String =
            "login?$LOGIN_ARG_MODE=${mode.name}" +
                username?.let { "&$LOGIN_ARG_USERNAME=${Uri.encode(it)}" }.orEmpty()
    }
}

const val LOGIN_ARG_MODE = "mode"
const val LOGIN_ARG_USERNAME = "username"

/**
 * Navigates to [route] and drops everything before it, so screens (and their view models) from
 * a previous account or login state can't be reached with back.
 */
fun NavController.navigateClearingStack(route: String) {
    navigate(route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}
