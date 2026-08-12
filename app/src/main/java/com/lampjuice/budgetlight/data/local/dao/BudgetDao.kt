package com.lampjuice.budgetlight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lampjuice.budgetlight.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity): Long

    @Query(
        """
            UPDATE budgets
            SET expenseLimit = :expenseLimit
            WHERE userId = :userId
            AND year = :year
            AND month = :month
        """,
    )
    suspend fun updateBudget(
        userId: Long,
        year: Int,
        month: Int,
        expenseLimit: Long,
    ): Int

    @Query(
        """
            SELECT *
            FROM budgets
            WHERE userId = :userId
            AND year = :year
            AND month = :month
            LIMIT 1
        """,
    )
    fun observeBudget(
        userId: Long,
        year: Int,
        month: Int,
    ): Flow<BudgetEntity?>

    @Query(
        """
            SELECT id
            FROM budgets
            WHERE userId = :userId
            AND year = :year
            AND month = :month
            LIMIT 1
        """,
    )
    suspend fun getBudgetId(
        userId: Long,
        year: Int,
        month: Int,
    ): Long?
}
