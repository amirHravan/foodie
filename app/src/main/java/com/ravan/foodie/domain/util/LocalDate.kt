package com.ravan.foodie.domain.util

import android.os.Build
import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

enum class DaysOfWeek(
    val id: Int,
    val dayName: String
) {
    SATURDAY(0, "Saturday"),
    SUNDAY(1, "Sunday"),
    MONDAY(2, "Monday"),
    TUESDAY(3, "Tuesday"),
    WEDNESDAY(4, "Wednesday"),
    THURSDAY(5, "Thursday"),
    FRIDAY(6, "Friday");

    companion object {
        fun fromName(name: String): DaysOfWeek {
            return when (name.lowercase(Locale.ROOT)) {
                "saturday" -> SATURDAY
                "sunday" -> SUNDAY
                "monday" -> MONDAY
                "tuesday" -> TUESDAY
                "wednesday" -> WEDNESDAY
                "thursday" -> THURSDAY
                "friday" -> FRIDAY
                else -> SATURDAY
            }
        }
    }
}

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

fun getToday(format: String = "yyyy-MM-dd"): String {
    val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now(ZoneId.of("Asia/Tehran"))
    } else {
        return ""
    }

    val formatter = DateTimeFormatter.ofPattern(format)
    val date = now.format(formatter)

    return date
}

fun getPreviousSaturday(): String {
    var now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now(ZoneId.of("Asia/Tehran"))
    } else {
        return ""
    }

    now = if (now.dayOfWeek == DayOfWeek.SATURDAY) {
        now.withHour(0).withMinute(0).withSecond(0).withNano(0)
    } else {
        now.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY))
            .withHour(0).withMinute(0).withSecond(0).withNano(0)
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val date = now.format(formatter)
    Log.d("ctime", date)

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
    Log.d("time", date)
    return date
}