package com.lampjuice.budgetlight.feature.budget.data.repository

import com.lampjuice.budgetlight.feature.budget.data.local.dao.AccountDao
import com.lampjuice.budgetlight.feature.budget.data.mapper.toDomain
import com.lampjuice.budgetlight.feature.budget.data.mapper.toEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.Account
import com.lampjuice.budgetlight.feature.budget.domain.repository.AccountRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountRepositoryImpl
@Inject
constructor(
    private val dao: AccountDao,
) : AccountRepository {
    override fun observeAccounts(userId: Long): Flow<List<Account>> = dao
        .observeAccounts(userId)
        .map { list ->
            list.map { it.toDomain() }
        }

    override fun observeCurrentAccount(userId: Long): Flow<Account?> = observeAccounts(userId)
        .map { accounts ->
            accounts.firstOrNull()
        }

    override suspend fun addAccount(account: Account): Long = dao.insertAccount(account.toEntity())
}
