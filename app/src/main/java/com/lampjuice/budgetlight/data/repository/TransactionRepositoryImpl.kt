package com.lampjuice.budgetlight.data.repository

import com.lampjuice.budgetlight.data.local.dao.TransactionDao
import com.lampjuice.budgetlight.data.mapper.toDomain
import com.lampjuice.budgetlight.data.mapper.toEntity
import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl
@Inject
constructor(
    private val dao: TransactionDao,
) : TransactionRepository {
    override fun observeTransactions(accountId: Long): Flow<List<Transaction>> = dao
        .observeTransactions(accountId)
        .map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(id: Long) {
        dao.deleteTransaction(id)
    }
}
