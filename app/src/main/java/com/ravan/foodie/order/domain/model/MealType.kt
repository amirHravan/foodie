package com.ravan.foodie.order.domain.model

import com.ravan.foodie.R

enum class MealType {
    SAHAR,
    BREAKFAST,
    LUNCH,
    EFTAR,
    DINNER,
    OTHER;

    fun getLocalName(): String {
        return when (this) {
            SAHAR -> "سحری"
            BREAKFAST -> "صبحانه"
            LUNCH -> "ناهار"
            DINNER -> "شام"
            EFTAR -> "افطار"
            OTHER -> "سایر"
        }
    }

    fun getAbbreviation(): String {
        return when (this) {
            SAHAR -> "سحری"
            BREAKFAST -> "صبحانه"
            LUNCH -> "ناهار"
            DINNER -> "شام"
            EFTAR -> "افطار"
            OTHER -> "سایر"
        }
    }

    fun getIcon(): Int {
        return when (this) {
            SAHAR -> R.drawable.ic_twilight
            BREAKFAST -> R.drawable.ic_twilight
            LUNCH -> R.drawable.ic_sun
            DINNER -> R.drawable.ic_moon
            EFTAR -> R.drawable.ic_moon
            OTHER -> R.drawable.ic_sun
        }
    }

    companion object {
        fun getMealTypeById(id: Int): MealType {
            return when (id) {
                2 -> SAHAR
                3 -> BREAKFAST
                1 -> LUNCH
                4 -> EFTAR
                5 -> DINNER
                else -> OTHER
            }
        }
    }
}