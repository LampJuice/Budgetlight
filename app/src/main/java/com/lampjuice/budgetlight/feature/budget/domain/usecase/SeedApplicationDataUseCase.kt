package com.lampjuice.budgetlight.feature.budget.domain.usecase

import jakarta.inject.Inject

class SeedApplicationDataUseCase @Inject constructor(
    private val seedCategoriesUseCase: SeedCategoriesUseCase,
    private val seedBudgetUseCase: SeedBudgetUseCase,
    private val seedDatabaseUseCase: SeedDatabaseUseCase,
) {

    suspend operator fun invoke(
        userId: Long,
    ) {
        seedCategoriesUseCase()

        seedBudgetUseCase(
            userId = userId,
        )

        seedDatabaseUseCase(
            userId = userId,
        )
    }
}
