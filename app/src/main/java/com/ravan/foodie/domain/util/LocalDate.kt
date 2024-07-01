package com.ravan.foodie.domain.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toLocalDayName(): String {
    return when (this.lowercase(Locale.ROOT)) {
        "saturday" -> "شنبه"
        "sunday" -> "یکشنبه"
        "monday" -> "دوشنبه"
        "tuesday" -> "سه شنبه"
        "wednesday" -> "چهارشنبه"
        "thursday" -> "پنجشنبه"
        "friday" -> "جمعه"
        else -> this
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return currentDateTime.format(formatter)
}


@RequiresApi(Build.VERSION_CODES.O)
fun getThisWeekSaturdayDate(): String {
    val currentDateTime = LocalDateTime.now()

    val daysUntilSaturday = DayOfWeek.SATURDAY.value - currentDateTime.dayOfWeek.value
    val daysToAdd = if (daysUntilSaturday < 0) daysUntilSaturday + 7 else daysUntilSaturday

    val upcomingSaturday = currentDateTime.plusDays(daysToAdd.toLong()).with(LocalTime.MIN)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return upcomingSaturday.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getNextSaturdayDate(): String {
    val currentDateTime = LocalDateTime.now()

    val daysUntilNextSaturday = DayOfWeek.SATURDAY.value - currentDateTime.dayOfWeek.value
    val daysToAdd =
        if (daysUntilNextSaturday <= 0) daysUntilNextSaturday + 7 else daysUntilNextSaturday

    val nextSaturday = currentDateTime.plusDays(daysToAdd.toLong()).with(LocalTime.MIN)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return nextSaturday.format(formatter)
}
