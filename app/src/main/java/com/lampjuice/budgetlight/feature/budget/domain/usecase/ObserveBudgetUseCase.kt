package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetRepository
import jakarta.inject.Inject

class ObserveBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository,
) {
    operator fun invoke(
        userId: Long,
        year: Int,
        month: Int,
    ) = repository.observeBudget(
        userId,
        year,
        month,
    )
}
