package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Budget
import com.lampjuice.budgetlight.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrentBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository,
) {
    operator fun invoke(userId: Long): Flow<Budget?> = repository.observeCurrentBudget(userId)
}
