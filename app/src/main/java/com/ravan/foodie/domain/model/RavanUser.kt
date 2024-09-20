package com.ravan.foodie.domain.model

import java.time.LocalDateTime

data class RavanUser(
    val userName: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    var credit: Int,
    val createdAt: LocalDateTime,
) {
    val fullName: String
        get() = "$firstName $lastName"

    fun increaseCredit(amount: Int) {
        credit += amount
    }
}