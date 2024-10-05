package com.ravan.foodie.autoreserve.db.converter

import androidx.room.TypeConverter

class DaysConverter {
    @TypeConverter
    fun fromDaysList(days: List<String>): String {
        return days.joinToString(",")
    }

    @TypeConverter
    fun toDaysList(data: String): List<String> {
        return data.split(",")
    }
}
