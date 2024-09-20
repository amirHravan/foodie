package com.ravan.foodie.domain.util

import android.os.Build
import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
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

fun getPreviousSaturday(): String {
    var now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now(ZoneId.of("Asia/Tehran"))
    } else {
        return "";
    }

    now = if (now.dayOfWeek == DayOfWeek.SATURDAY) {
        now.withHour(0).withMinute(0).withSecond(0).withNano(0)
    } else {
        now.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY))
            .withHour(0).withMinute(0).withSecond(0).withNano(0)
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date = now.format(formatter)

    Log.d("prev sat", date)

    return date
}

fun getNextSaturday(): String {
    // Get current date and time
    var now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now(ZoneId.of("Asia/Tehran"))
    } else {
        return ""
    }

    // If today is Saturday, find the next Saturday
    now = if (now.dayOfWeek == DayOfWeek.SATURDAY) {
        now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
    } else {
        // If today is not Saturday, find the upcoming Saturday
        now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
    }

    // Set the time to 00:00:00
    now = now.withHour(0).withMinute(0).withSecond(0).withNano(0)

    // Format the date and time to the required pattern
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date = now.format(formatter)
    Log.d("next sat", date)

    return date
}

fun main() {
    println(getNextSaturday())  // Output example: 2024-09-21 00:00:00
}