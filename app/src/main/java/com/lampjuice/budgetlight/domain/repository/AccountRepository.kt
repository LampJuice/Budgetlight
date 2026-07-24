package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun observeAccounts(userId: Long): Flow<List<Account>>

    suspend fun addAccount(account: Account)

}
