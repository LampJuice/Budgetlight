package com.lampjuice.budgetlight.ui.util

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

fun Long.formatMoney(): String {
    val formatter = NumberFormat.getNumberInstance(
        Locale("ru", "RU"),
    )
    val value = formatter.format(abs(this))

    return "$value ₽"
}
