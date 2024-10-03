package com.ravan.foodie.dailysell.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdHrefDto(
    @SerialName("id") val id: Int = -1,
    @SerialName("href") val href: String = "",
)
