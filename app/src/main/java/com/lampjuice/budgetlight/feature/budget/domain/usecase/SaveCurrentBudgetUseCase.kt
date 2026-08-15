package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.domain.defaultdata.DefaultBudgetFactory
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class SaveCurrentBudgetUseCase @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val budgetCategoryRepository: BudgetCategoryRepository,
) {
    suspend operator fun invoke(
        userId: Long,
        expenseLimit: Long,
    ) {
        val now = LocalDate.now()

        val existingBudget = budgetRepository
            .observeBudget(
                userId = userId,
                year = now.year,
                month = now.monthValue,
            )
            .first()

        val budgetId = budgetRepository.saveBudget(
            userId = userId,
            year = now.year,
            month = now.monthValue,
            expenseLimit = expenseLimit,
        )

        if (existingBudget == null) {
            val categories = categoryRepository
                .observeCategories()
                .first()
            val budgetCategories = DefaultBudgetFactory.createBudgetCategories(
                budgetId = budgetId,
                categories = categories,
            )
            budgetCategoryRepository.insertAll(
                budgetCategories = budgetCategories,
            )
        }
    }
}
