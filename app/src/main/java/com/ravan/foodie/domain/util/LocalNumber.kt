package com.ravan.foodie.domain.util

fun String.toLocalNumber(): String {
    val copy = this.toCharArray().map {
        when (it) {
            '0' -> '۰'
            '1' -> '۱'
            '2' -> '۲'
            '3' -> '۳'
            '4' -> '۴'
            '5' -> '۵'
            '6' -> '۶'
            '7' -> '۷'
            '8' -> '۸'
            '9' -> '۹'
            else -> {
                it
            }
        }
    }.joinToString("")

    if (copy.length > 1 && copy.first() == '-') {
        return "${copy.drop(1)}-"
    }

    return copy
}

fun String.toEnglishNumber(): String {
    val copy = this.toCharArray().map {
        when (it) {
            '۰' -> '0'
            '۱' -> '1'
            '۲' -> '2'
            '۳' -> '3'
            '۴' -> '4'
            '۵' -> '5'
            '۶' -> '6'
            '۷' -> '7'
            '۸' -> '8'
            '۹' -> '9'
            else -> {
                it
            }
        }
    }.joinToString("")
    return copy
}