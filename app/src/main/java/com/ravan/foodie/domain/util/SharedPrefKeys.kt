package com.ravan.foodie.domain.util

enum class SharedPrefKeys(
    val key: String,
) {
    Username("user_name"),
    Password("password"),
    LoginToken("token")
}