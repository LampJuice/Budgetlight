package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.domain.model.TransactionInfo
import com.lampjuice.budgetlight.ui.home.model.TransactionUi
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

fun TransactionInfo.toUi(): TransactionUi = TransactionUi(
    id = id,
    title = title,
    amount = amount,
    date = date.toTransactionDateString(),
    type = type,
    categoryName = categoryName,
    categoryIcon = categoryIcon,
)
