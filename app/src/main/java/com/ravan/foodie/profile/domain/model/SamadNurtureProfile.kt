package com.ravan.foodie.profile.domain.model

data class SamadNurtureProfile(
    val firstName: String,
    val lastName: String,
    val username: String,
    val creditTransfers: List<CreditTransfer>,
    val credit: Int,
)

