package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.BudgetCategory
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetCategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveBudgetCategoriesUseCase @Inject constructor(
    private val repository: BudgetCategoryRepository,
) {
    operator fun invoke(budgetId: Long): Flow<List<BudgetCategory>> = repository.observeBudgetCategories(budgetId)
}
