package com.ravan.foodie.login.domain.model

data class SamadToken(
    val accessToken: String,
    val expiresIn: Int,
    val firstName: String,
    val lastName: String,
    val nationalCode: String,
    val refreshToken: String,
    val tokenType: String,
    val userId: Int
)