package com.lampjuice.budgetlight.feature.budget.data.repository

import com.lampjuice.budgetlight.feature.budget.data.local.dao.BudgetDao
import com.lampjuice.budgetlight.feature.budget.data.local.entity.BudgetEntity
import com.lampjuice.budgetlight.feature.budget.data.mapper.toDomain
import com.lampjuice.budgetlight.feature.budget.domain.model.Budget
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetRepositoryImpl @Inject constructor(
    private val dao: BudgetDao,
) : BudgetRepository {

    override suspend fun saveBudget(
        userId: Long,
        year: Int,
        month: Int,
        expenseLimit: Long,
    ): Long {
        val updateRows = dao.updateBudget(
            userId,
            year,
            month,
            expenseLimit,
        )
        if (updateRows == 0) {
            dao.insert(
                BudgetEntity(
                    id = 0,
                    userId = userId,
                    year = year,
                    month = month,
                    plannedIncome = 0,
                    expenseLimit = expenseLimit,
                ),
            )
        }
        return dao.getBudgetId(
            userId,
            year,
            month,
        ) ?: error("Budget was updated but it's id not found")
    }

    override fun observeBudget(
        userId: Long,
        year: Int,
        month: Int,
    ): Flow<Budget?> = dao.observeBudget(
        userId,
        year,
        month,
    ).map { it?.toDomain() }
}
