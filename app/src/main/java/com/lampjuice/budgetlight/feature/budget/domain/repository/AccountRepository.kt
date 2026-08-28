package com.lampjuice.budgetlight.feature.budget.domain.repository

import com.lampjuice.budgetlight.feature.budget.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun observeAccounts(userId: Long): Flow<List<Account>>

    fun observeCurrentAccount(userId: Long): Flow<Account?>
    suspend fun addAccount(account: Account): Long
}
