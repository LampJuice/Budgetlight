package com.lampjuice.budgetlight.feature.budget.data.repository

import com.lampjuice.budgetlight.feature.budget.data.local.dao.BudgetCategoryDao
import com.lampjuice.budgetlight.feature.budget.data.mapper.toDomain
import com.lampjuice.budgetlight.feature.budget.data.mapper.toEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.BudgetCategory
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetCategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BudgetCategoryRepositoryImpl @Inject constructor(
    private val dao: BudgetCategoryDao,
) : BudgetCategoryRepository {
    override fun observeBudgetCategories(
        budgetId: Long,
    ): Flow<List<BudgetCategory>> = dao.observeBudgetCategories(budgetId).map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun insert(budgetCategory: BudgetCategory): Long = dao.insert(
        budgetCategory.toEntity(),
    )

    override suspend fun insertAll(budgetCategories: List<BudgetCategory>) {
        dao.insertAll(budgetCategories.map { it.toEntity() })
    }

    override suspend fun updatePlannedAmount(
        budgetId: Long,
        categoryId: Long,
        plannedAmount: Long,
    ) {
        dao.updatePlannedAmount(
            budgetId = budgetId,
            categoryId = categoryId,
            plannedAmount = plannedAmount,
        )
    }
}
