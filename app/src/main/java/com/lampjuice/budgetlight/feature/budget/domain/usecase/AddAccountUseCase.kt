package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.Account
import com.lampjuice.budgetlight.feature.budget.domain.repository.AccountRepository
import jakarta.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(account: Account): Long = accountRepository.addAccount(account)
}
