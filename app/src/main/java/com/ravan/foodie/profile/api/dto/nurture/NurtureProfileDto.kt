package com.ravan.foodie.profile.api.dto.nurture

import com.ravan.foodie.profile.domain.model.SamadNurtureProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NurtureProfileDto(
    @SerialName("credit") val credit: Int,
    @SerialName("creditChanges") val creditChanges: List<CreditChangeDto>,
    @SerialName("user") val nurtureProfileUserDto: NurtureProfileUserDto
//    @SerialName("createDate") val createDate: String,
//    @SerialName("id") val id: Int,
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