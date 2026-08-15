package com.lampjuice.budgetlight.feature.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lampjuice.budgetlight.feature.budget.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDao {
    @Query(
        """
            SELECT * FROM transactions
            WHERE accountId = :accountId
            ORDER BY date DESC, id DESC
        """,
    )
    fun observeTransactions(accountId: Long): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Query(
        """
            DELETE FROM transactions
            WHERE id = :id
        """,
    )
    suspend fun deleteTransaction(id: Long)

    @Query(
        """
            SELECT *
            FROM transactions
            WHERE accountId = :accountId
            AND categoryId = :categoryId
            AND type = 'EXPENSE'
            ORDER BY date DESC, id DESC
        """,
    )
    fun observeTransactionByCategory(accountId: Long, categoryId: Long): Flow<List<TransactionEntity>>

    @Query(
        """
            SELECT *
            FROM transactions
            WHERE accountId = :accountId
            AND type = 'EXPENSE'
            ORDER BY date DESC, id DESC
        """,
    )
    fun observeExpenses(accountId: Long): Flow<List<TransactionEntity>>

    @Query(
        """
            SELECT *
            FROM transactions
            WHERE accountId = :accountId
            AND type = 'EXPENSE'
            AND date >= :startDate
            AND date < :endDate
            ORDER BY date DESC, id DESC
        """,
    )
    fun observeExpensesForPeriod(
        accountId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<TransactionEntity>>
}
