package com.lampjuice.budgetlight.ui.transactions

import com.lampjuice.budgetlight.domain.model.TransactionType

enum class TransactionFilter {
    ALL,
    EXPENSE,
    INCOME,
}

fun TransactionFilter.matches(type: TransactionType): Boolean = when (this) {
    TransactionFilter.ALL -> true
    TransactionFilter.EXPENSE -> type == TransactionType.EXPENSE
    TransactionFilter.INCOME -> type == TransactionType.INCOME
}
