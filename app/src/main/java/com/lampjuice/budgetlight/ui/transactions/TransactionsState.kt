package com.lampjuice.budgetlight.ui.transactions

import com.lampjuice.budgetlight.ui.home.model.TransactionUi

data class TransactionsState(
    val transactions: List<TransactionUi> = emptyList(),
    val filter: TransactionFilter = TransactionFilter.ALL,
    val isLoading: Boolean = true,
)
