package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Account
import com.lampjuice.budgetlight.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveCurrentAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    operator fun invoke(userId: Long): Flow<Account?> = repository.observeCurrentAccount(userId)
}
