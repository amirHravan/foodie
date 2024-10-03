package com.ravan.foodie.domain.util

enum class SharedPrefKeys(
    val key: String,
) {
    Username("USERNAME"),
    Password("PASSWORD"),
    AccessToken("ACCESS_TOKEN"),
    RefreshToken("REFRESH_TOKEN"),
    NotificationsEnabled("NOTIFICATIONS_ENABLED"),

}