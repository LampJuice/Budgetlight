package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.Account
import com.lampjuice.budgetlight.feature.budget.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class InitializeUserDataUseCase @Inject constructor(
    private val seedCategoriesUseCase: SeedCategoriesUseCase,
    private val seedBudgetUseCase: SeedBudgetUseCase,
    private val accountRepository: AccountRepository,
    private val addAccountUseCase: AddAccountUseCase,
) {

    suspend operator fun invoke(
        userId: Long,
    ) {
        seedCategoriesUseCase()

        seedBudgetUseCase(
            userId = userId,
        )

        val currentAccount = accountRepository
            .observeCurrentAccount(userId)
            .first()

        if (currentAccount == null) {
            addAccountUseCase(
                Account(
                    id = 0,
                    userId = userId,
                    name = "Основной счет",
                ),
            )
        }
    }
}
