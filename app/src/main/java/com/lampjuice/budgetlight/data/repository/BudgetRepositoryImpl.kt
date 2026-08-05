package com.lampjuice.budgetlight.data.repository

import com.lampjuice.budgetlight.data.local.dao.BudgetDao
import com.lampjuice.budgetlight.data.mapper.toDomain
import com.lampjuice.budgetlight.data.mapper.toEntity
import com.lampjuice.budgetlight.domain.model.Budget
import com.lampjuice.budgetlight.domain.repository.BudgetRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetRepositoryImpl @Inject constructor(
    private val dao: BudgetDao,
) : BudgetRepository {
    override fun observeCurrentBudget(
        userId: Long,
    ): Flow<Budget?> = dao.observeCurrentBudget(userId).map { it?.toDomain() }

    override suspend fun insertBudget(budget: Budget) {
        dao.insertBudget(budget.toEntity())
    }
}
