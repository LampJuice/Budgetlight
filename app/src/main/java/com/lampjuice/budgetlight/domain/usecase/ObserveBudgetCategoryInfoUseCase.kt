package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.BudgetCategoryInfo
import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveBudgetCategoryInfoUseCase @Inject constructor(
    private val budgetCategoryRepository: BudgetCategoryRepository,
    private val categoryRepository: CategoryRepository,
) {

    operator fun invoke(budgetId: Long): Flow<List<BudgetCategoryInfo>> = combine(
        budgetCategoryRepository.observeBudgetCategories(budgetId),
        categoryRepository.observeCategories(),
    ) { budgetCategories, categories ->
        budgetCategories.mapNotNull { budgetCategory ->
            val category =
                categories.find { it.id == budgetCategory.categoryId }
            category?.let {
                BudgetCategoryInfo(
                    categoryId = it.id,
                    name = it.name,
                    icon = it.icon,
                    plannedAmount = budgetCategory.plannedAmount,
                )
            }
        }
    }
}
