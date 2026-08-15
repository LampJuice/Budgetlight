package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.BudgetCategory
import com.lampjuice.budgetlight.feature.budget.domain.model.Category
import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class AddCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val budgetCategoryRepository: BudgetCategoryRepository,
    private val budgetRepository: BudgetRepository,
) {
    suspend operator fun invoke(
        userId: Long,
        name: String,
        icon: CategoryIcon,
        type: TransactionType,
    ) {
        require(name.isNotBlank()) { "Category name cannot be blank" }

        val categoryId = categoryRepository.insert(
            Category(
                id = 0,
                name = name,
                icon = icon,
                type = type,
            ),
        )

        if (type != TransactionType.EXPENSE) {
            return
        }

        val now = LocalDate.now()

        val budget = budgetRepository
            .observeBudget(
                userId = userId,
                year = now.year,
                month = now.monthValue,
            )
            .first()
            ?: return

        budgetCategoryRepository.insert(
            BudgetCategory(
                id = 0,
                budgetId = budget.id,
                categoryId = categoryId,
                plannedAmount = 0L,
            ),
        )
    }
}
