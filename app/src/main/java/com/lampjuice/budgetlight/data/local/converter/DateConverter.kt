package com.lampjuice.budgetlight.data.local.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

class DateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long {
        return date
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    }

    @TypeConverter
    fun toLocalDate(time: Long): LocalDate {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
    }
}
