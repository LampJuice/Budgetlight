package com.lampjuice.budgetlight.feature.budget.ui.transactions

import com.lampjuice.budgetlight.feature.budget.ui.home.model.TransactionUi

data class TransactionsState(
    val transactions: List<TransactionUi> = emptyList(),
    val filter: TransactionFilter = TransactionFilter.ALL,
    val isLoading: Boolean = true,
)
