package com.lampjuice.budgetlight.ui.home

import com.lampjuice.budgetlight.ui.home.model.BudgetSummaryUi
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.home.model.TransactionUi

data class HomeState(
    val userName: String = "",
    val budget: BudgetSummaryUi? = null,
    val categories: List<CategoryBudgetUi> = emptyList(),
    val recentTransactions: List<TransactionUi> = emptyList(),
    val isLoading: Boolean = true,
)
