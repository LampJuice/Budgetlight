package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun observeTransactions(accountId: Long): Flow<List<Transaction>>

    suspend fun addTransaction(transaction: Transaction)

    suspend fun deleteTransaction(id: Long)

}
