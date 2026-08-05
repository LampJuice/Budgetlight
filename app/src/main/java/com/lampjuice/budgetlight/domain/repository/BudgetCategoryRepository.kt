package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.BudgetCategory
import kotlinx.coroutines.flow.Flow

interface BudgetCategoryRepository {
    fun observeBudgetCategories(budgetId: Long): Flow<List<BudgetCategory>>

    suspend fun insertBudgetCategory(budgetCategory: BudgetCategory)

    suspend fun insertAll(budgetCategories: List<BudgetCategory>)
}
