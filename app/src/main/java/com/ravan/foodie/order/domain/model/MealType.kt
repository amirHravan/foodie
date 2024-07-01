package com.ravan.foodie.order.domain.model

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