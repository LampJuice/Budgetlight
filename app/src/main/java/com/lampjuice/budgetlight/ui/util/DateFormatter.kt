package com.lampjuice.budgetlight.ui.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateFormatter =
    DateTimeFormatter.ofPattern(
        "d MMMM",
        Locale.forLanguageTag("ru-RU"),
    )

private val dateFormatterWithYear =
    DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale.forLanguageTag("ru-RU"),
    )

fun LocalDate.toTransactionDateString(
    today: LocalDate = LocalDate.now(),
): String = when {
    this == today -> "Сегодня"
    this == today.minusDays(1) -> "Вчера"
    year == today.year -> format(dateFormatter)
    else -> format(dateFormatterWithYear)
}

private val monthFormatter =
    DateTimeFormatter.ofPattern(
        "LLLL yyyy",
        Locale.forLanguageTag("ru-RU"),
    )

fun LocalDate.toMonthYearString(): String = format(monthFormatter).replaceFirstChar { it.uppercase() }
