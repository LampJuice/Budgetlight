package com.lampjuice.budgetlight.ui.home

data class HomeState(
    val balance: Long = 0,
    val income: Long = 0,
    val expense: Long = 0,
    val transactions: List<TransactionUi> = emptyList(),
    val isLoading: Boolean = true,
)
