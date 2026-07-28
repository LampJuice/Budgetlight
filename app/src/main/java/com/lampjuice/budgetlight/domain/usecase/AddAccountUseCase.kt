package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Account
import com.lampjuice.budgetlight.domain.repository.AccountRepository
import jakarta.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(account: Account): Long = accountRepository.addAccount(account)
}
