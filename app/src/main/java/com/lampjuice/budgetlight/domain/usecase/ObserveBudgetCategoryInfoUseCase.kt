package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.BudgetCategoryInfo
import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveBudgetCategoryInfoUseCase @Inject constructor(
    private val budgetCategoryRepository: BudgetCategoryRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
) {

    operator fun invoke(
        budgetId: Long,
        accountId: Long,
    ): Flow<List<BudgetCategoryInfo>> = combine(
        budgetCategoryRepository.observeBudgetCategories(budgetId),
        categoryRepository.observeCategories(),
        transactionRepository.observeExpenses(accountId),
    ) { budgetCategories, categories, expenses ->
        budgetCategories.mapNotNull { budgetCategory ->
            val category =
                categories.find { it.id == budgetCategory.categoryId }
            val spentAmount = expenses.filter { it.categoryId == budgetCategory.categoryId }.sumOf { it.amount }
            category?.let {
                BudgetCategoryInfo(
                    categoryId = it.id,
                    name = it.name,
                    icon = it.icon,
                    plannedAmount = budgetCategory.plannedAmount,
                    spentAmount = spentAmount,
                )
            }
        }
    }
}
