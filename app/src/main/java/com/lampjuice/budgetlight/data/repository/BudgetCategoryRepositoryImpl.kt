package com.lampjuice.budgetlight.data.repository

import com.lampjuice.budgetlight.data.local.dao.BudgetCategoryDao
import com.lampjuice.budgetlight.data.mapper.toDomain
import com.lampjuice.budgetlight.data.mapper.toEntity
import com.lampjuice.budgetlight.domain.model.BudgetCategory
import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
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
}
