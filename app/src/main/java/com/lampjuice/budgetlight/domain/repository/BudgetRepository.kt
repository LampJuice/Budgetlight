package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {

    suspend fun saveBudget(
        userId: Long,
        year: Int,
        month: Int,
        expenseLimit: Long,
    ): Long

    fun observeBudget(
        userId: Long,
        year: Int,
        month: Int,
    ): Flow<Budget?>
}
