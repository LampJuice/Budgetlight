package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {
    fun observeTransactions(accountId: Long): Flow<List<Transaction>>

    suspend fun addTransaction(transaction: Transaction)

    suspend fun deleteTransaction(id: Long)

    fun observeTransactionByCategory(accountId: Long, categoryId: Long): Flow<List<Transaction>>

    fun observeExpenses(accountId: Long): Flow<List<Transaction>>

    fun observeExpensesForPeriod(
        accountId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<Transaction>>
}
