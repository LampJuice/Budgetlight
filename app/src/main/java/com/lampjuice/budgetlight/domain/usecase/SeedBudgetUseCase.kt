package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.defaultdata.DefaultBudgetFactory
import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.domain.repository.BudgetRepository
import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class SeedBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val budgetCategoryRepository: BudgetCategoryRepository,
) {
    suspend operator fun invoke(
        userId: Long,
    ) {
        val now = LocalDate.now()
        val existingBudget = budgetRepository
            .observeBudget(
                userId = userId,
                year = now.year,
                month = now.monthValue,
            )
            .first()
        if (existingBudget != null) {
            return
        }

        val budget = DefaultBudgetFactory.createBudget(
            userId = userId,
        )
        val budgetId = budgetRepository.saveBudget(budget.userId, budget.year, budget.month, budget.expenseLimit)

        val categories = categoryRepository
            .observeCategories()
            .first()

        val budgetCategories =
            DefaultBudgetFactory.createBudgetCategories(
                budgetId = budgetId,
                categories = categories,
            )
        budgetCategoryRepository.insertAll(
            budgetCategories = budgetCategories,
        )
    }
}
