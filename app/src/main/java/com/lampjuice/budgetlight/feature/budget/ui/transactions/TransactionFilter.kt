package com.lampjuice.budgetlight.feature.budget.ui.transactions

import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType

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
