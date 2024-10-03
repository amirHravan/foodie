package com.ravan.foodie.login.domain.model

data class SamadToken(
    val accessToken: String,
    val refreshToken: String,
)