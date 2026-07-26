package com.lampjuice.budgetlight.ui.home

import com.lampjuice.budgetlight.domain.model.Transaction

data class HomeState(
    val balance: Long = 0,
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = true,
)
