package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.repository.BudgetRepository
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
