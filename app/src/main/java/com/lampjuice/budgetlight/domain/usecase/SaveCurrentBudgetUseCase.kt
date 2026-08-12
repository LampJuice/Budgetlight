package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.repository.BudgetRepository
import jakarta.inject.Inject
import java.time.LocalDate

class SaveCurrentBudgetUseCase @Inject constructor(
    private val repository: BudgetRepository,
) {
    suspend operator fun invoke(
        userId: Long,
        expenseLimit: Long,
    ) {
        val now = LocalDate.now()

        repository.saveBudget(
            userId = userId,
            year = now.year,
            month = now.monthValue,
            expenseLimit = expenseLimit,
        )
    }
}
