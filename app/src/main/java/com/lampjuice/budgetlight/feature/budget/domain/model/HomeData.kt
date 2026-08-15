package com.lampjuice.budgetlight.feature.budget.domain.model

data class HomeData(
    val budget: Budget?,
    val transactionsInfo: List<TransactionInfo>,
    val budgetCategories: List<BudgetCategoryInfo>,
)
