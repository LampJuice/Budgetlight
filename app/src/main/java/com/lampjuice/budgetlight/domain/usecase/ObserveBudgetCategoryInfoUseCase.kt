package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Budget
import com.lampjuice.budgetlight.domain.model.BudgetCategoryInfo
import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class ObserveBudgetCategoryInfoUseCase @Inject constructor(
    private val budgetCategoryRepository: BudgetCategoryRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository,
) {

    operator fun invoke(
        budget: Budget,
        accountId: Long,
    ): Flow<List<BudgetCategoryInfo>> {
        val startDate = LocalDate.of(budget.year, budget.month, 1)
        val endDate = startDate.plusMonths(1)
        return combine(
            budgetCategoryRepository.observeBudgetCategories(budgetId = budget.id),
            categoryRepository.observeCategories(),
            transactionRepository.observeExpensesForPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate,
            ),
        ) { budgetCategories, categories, expenses ->
            budgetCategories.mapNotNull { budgetCategory ->

                val category =
                    categories.find { it.id == budgetCategory.categoryId }
                val spentAmount = expenses.filter { it.categoryId == budgetCategory.categoryId }
                    .sumOf { it.amount }
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
}
