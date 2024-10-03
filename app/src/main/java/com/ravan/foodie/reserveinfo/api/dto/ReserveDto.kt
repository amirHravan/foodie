package com.ravan.foodie.reserveinfo.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReserveDto(
    @SerialName("selfName") val selfName: String = "",
    @SerialName("consumed") val consumed: Boolean = false,
    @SerialName("foodNames") val foodNames: String = "",
    @SerialName("id") val id: Int = 0,
    @SerialName("price") val price: Int = 0,
    @SerialName("timeDistanceUntilToday") val timeDistanceUntilToday: Int = 0,
//    @SerialName("besideFoodNames") val besideFoodNames: String,
//    @SerialName("changableSelf") val changableSelf: Boolean,
//    @SerialName("consumedSubsidyPrice") val consumedSubsidyPrice: Int,
//    @SerialName("decreasedSelectedCountAsSellFreeFood") val decreasedSelectedCountAsSellFreeFood: Boolean,
//    @SerialName("deleteAsSellFreeFood") val deleteAsSellFreeFood: Boolean,
//    @SerialName("foodId") val foodId: Int,
//    @SerialName("foodTypeId") val foodTypeId: Int,
//    @SerialName("foodTypeTitle") val foodTypeTitle: String,
//    @SerialName("forSale") val forSale: Boolean,
//    @SerialName("freeFoodSelected") val freeFoodSelected: Boolean,
//    @SerialName("fullName") val fullName: String,
//    @SerialName("groupId") val groupId: Int,
//    @SerialName("key") val key: String,
//    @SerialName("mealTypeId") val mealTypeId: Int,
//    @SerialName("priorReserveDate") val priorReserveDate: String,
//    @SerialName("priorReserveDateStr") val priorReserveDateStr: String,
//    @SerialName("programDate") val programDate: String,
//    @SerialName("programDateStr") val programDateStr: String,
//    @SerialName("programDateTime") val programDateTime: Long,
//    @SerialName("programId") val programId: Int,
//    @SerialName("remainedCount") val remainedCount: Int,
//    @SerialName("saleableFreeFood") val saleableFreeFood: Boolean,
//    @SerialName("saleableFreeFoodToll") val saleableFreeFoodToll: Int,
//    @SerialName("selected") val selected: Boolean,
//    @SerialName("selectedCount") val selectedCount: Int,
//    @SerialName("selfChangeToll") val selfChangeToll: Int,
//    @SerialName("selfCode") val selfCode: String,
//    @SerialName("selfCodeName") val selfCodeName: String,
//    @SerialName("selfId") val selfId: Int,
//    @SerialName("sold") val sold: Boolean,
//    @SerialName("userId") val userId: Int
)