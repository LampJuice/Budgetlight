package com.lampjuice.budgetlight.data.local.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

class DateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long = date
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()

    @TypeConverter
    fun toLocalDate(time: Long): LocalDate = Instant
        .ofEpochMilli(time)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}
