package com.ravan.foodie.profile.api.dto.nurture

import com.ravan.foodie.profile.domain.model.SamadNurtureProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NurtureProfileDto(
    @SerialName("createDate") val createDate: String,
    @SerialName("credit") val credit: Int,
    @SerialName("creditChanges") val creditChanges: List<CreditChangeDto>,
    @SerialName("id") val id: Int,
    @SerialName("user") val nurtureProfileUserDto: NurtureProfileUserDto
)

fun NurtureProfileDto.toSamadNurtureProfile(): SamadNurtureProfile {
    return SamadNurtureProfile(
        firstName = nurtureProfileUserDto.firstName,
        lastName = nurtureProfileUserDto.lastName,
        username = nurtureProfileUserDto.username,
        credit = credit,
        creditTransfers = creditChanges.map { it.toCreditTransfer() },
    )
}