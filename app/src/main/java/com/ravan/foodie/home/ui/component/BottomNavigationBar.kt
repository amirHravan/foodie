package com.ravan.foodie.home.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.FoodieRoutes
import com.ravan.foodie.home.ui.model.BottomNavItems
import okhttp3.internal.toImmutableList


private val BOTTOM_NAV_ITEMS = listOf(
    BottomNavItems.AutoReserve,
    BottomNavItems.Reserve,
    BottomNavItems.ReserveInfo,
    BottomNavItems.DailySell,
    BottomNavItems.Profile
).toImmutableList()

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = RavanTheme.colors.background.primary,
        contentColor = RavanTheme.colors.text.onPrimary,
        elevation = 8.dp,
    ) {
        val whiteList = remember(true) {
            listOf(
                FoodieRoutes.AutomaticReservationScreen.route,
                FoodieRoutes.FoodPriorityScreen.route,
                FoodieRoutes.ReservationInfoScreen.route,
                FoodieRoutes.DailySaleScreen.route,
                FoodieRoutes.ProfileScreen.route,
                FoodieRoutes.ReservableScreen.route,
                FoodieRoutes.SettingsScreen.route,
            ).toImmutableList()
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        if (currentRoute in whiteList) {
            BOTTOM_NAV_ITEMS.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(FoodieRoutes.ReservationInfoScreen.route)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.iconRes),
                            tint = RavanTheme.colors.icon.onPrimary,
                            contentDescription = null,
                        )
                    },
                    selectedContentColor = RavanTheme.colors.icon.onPrimary,
                    unselectedContentColor = RavanTheme.colors.icon.onPrimary.copy(alpha = 0.5f)
//                label = {
//                    Text(
//                        text = item.name,
//                        color = RavanTheme.colors.text.onPrimary,
//                        style = RavanTheme.typography.h6,
////                        modifier = Modifier.padding(4.dp)
//                    )
//                }
                )
            }
        }
    }
}