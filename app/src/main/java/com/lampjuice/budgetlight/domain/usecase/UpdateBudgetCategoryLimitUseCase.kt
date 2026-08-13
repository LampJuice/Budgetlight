package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.repository.BudgetCategoryRepository
import jakarta.inject.Inject

class UpdateBudgetCategoryLimitUseCase @Inject constructor(
    private val budgetCategoryRepository: BudgetCategoryRepository,
) {
    suspend operator fun invoke(
        budgetId: Long,
        categoryId: Long,
        plannedAmount: Long,
    ) {
        require(plannedAmount > 0) {
            "Planned amount must be greater than zero"
        }

        budgetCategoryRepository.updatePlannedAmount(
            budgetId = budgetId,
            categoryId = categoryId,
            plannedAmount = plannedAmount,
        )
    }
}
