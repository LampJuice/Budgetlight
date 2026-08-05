package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.BudgetCategory
import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveBudgetCategoriesUseCase @Inject constructor(
    private val repository: BudgetCategoryRepository,
) {
    operator fun invoke(budgetId: Long): Flow<List<BudgetCategory>> = repository.observeBudgetCategories(budgetId)
}
