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
    return copy
}