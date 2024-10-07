package com.ravan.foodie.home.ui.model

import com.ravan.foodie.R
import com.ravan.foodie.domain.util.FoodieRoutes

sealed class BottomNavItems(
    val name: String,
    val iconRes: Int,
    val route: String,
    val badgeCount: Int = 0
) {
    data object AutoReserve : BottomNavItems("رزرو اتوماتیک", R.drawable.ic_smart_toy, FoodieRoutes.AutomaticReservationScreen.route)
    data object Reserve : BottomNavItems("رزرو", R.drawable.ic_lunch, FoodieRoutes.ReservableScreen.route)
    data object ReserveInfo : BottomNavItems("وضعیت رزروها", R.drawable.ic_calender, FoodieRoutes.ReservationInfoScreen.route)
    data object DailySell : BottomNavItems("روزفروش", R.drawable.ic_shopping_cart, FoodieRoutes.DailySaleScreen.route)
    data object Profile : BottomNavItems("پروفایل", R.drawable.ic_profile, FoodieRoutes.ProfileScreen.route)
}
