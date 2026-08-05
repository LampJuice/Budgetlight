package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun observeCurrentBudget(userId: Long): Flow<Budget?>

    suspend fun insertBudget(budget: Budget)
}
