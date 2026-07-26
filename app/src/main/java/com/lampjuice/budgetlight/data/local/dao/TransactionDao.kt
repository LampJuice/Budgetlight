package com.lampjuice.budgetlight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lampjuice.budgetlight.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(
        """
            SELECT * FROM transactions
            WHERE accountId = :accountId
            ORDER BY date DESC
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
}
