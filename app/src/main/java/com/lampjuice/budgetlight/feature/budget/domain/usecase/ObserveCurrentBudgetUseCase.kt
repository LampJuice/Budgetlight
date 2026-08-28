package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.Budget
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ObserveCurrentBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository,
) {
    operator fun invoke(userId: Long): Flow<Budget?> {
        val now = LocalDate.now()
        return repository.observeBudget(
            userId,
            year = now.year,
            month = now.monthValue,
        )
    }
}
