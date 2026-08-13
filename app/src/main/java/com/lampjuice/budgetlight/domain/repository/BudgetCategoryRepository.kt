package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.BudgetCategory
import kotlinx.coroutines.flow.Flow

interface BudgetCategoryRepository {
    fun observeBudgetCategories(budgetId: Long): Flow<List<BudgetCategory>>

    suspend fun insert(budgetCategory: BudgetCategory): Long

    suspend fun insertAll(budgetCategories: List<BudgetCategory>)

    suspend fun updatePlannedAmount(
        budgetId: Long,
        categoryId: Long,
        plannedAmount: Long,
    )
}
