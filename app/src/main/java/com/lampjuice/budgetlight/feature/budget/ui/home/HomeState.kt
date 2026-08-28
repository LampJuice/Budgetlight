package com.lampjuice.budgetlight.feature.budget.ui.home

import com.lampjuice.budgetlight.feature.budget.ui.home.model.BudgetSummaryUi
import com.lampjuice.budgetlight.feature.budget.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.feature.budget.ui.home.model.TransactionUi

data class HomeState(
    val userName: String = "",
    val month: String = "",
    val budget: BudgetSummaryUi? = null,
    val categories: List<CategoryBudgetUi> = emptyList(),
    val recentTransactions: List<TransactionUi> = emptyList(),
)
