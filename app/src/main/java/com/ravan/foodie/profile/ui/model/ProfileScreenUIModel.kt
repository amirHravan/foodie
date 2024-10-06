package com.ravan.foodie.profile.ui.model

import com.ravan.foodie.profile.domain.model.SamadNurtureProfile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ProfileScreenUIModel(
    val firstName: String,
    val lastName: String,
    val credit: String,
    val userName: String,
    val creditTransfers: ImmutableList<CreditTransferUIModel>
) {
    val fullName: String
        get() = "$firstName $lastName"
}

fun SamadNurtureProfile.toProfileScreenUIModel(): ProfileScreenUIModel {
    return ProfileScreenUIModel(
        firstName = firstName,
        lastName = lastName,
        credit = credit.toString(),
        userName = username,
        creditTransfers = creditTransfers.map { it.toCreditTransferUIModel() }.toImmutableList()
    )
}