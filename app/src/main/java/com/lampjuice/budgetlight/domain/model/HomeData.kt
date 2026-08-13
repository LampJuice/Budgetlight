package com.lampjuice.budgetlight.domain.model

data class HomeData(
    val budget: Budget?,
    val transactions: List<Transaction>,
    val transactionsInfo: List<TransactionInfo>,
    val budgetCategories: List<BudgetCategoryInfo>,
)
