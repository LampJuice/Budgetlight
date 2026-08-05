package com.lampjuice.budgetlight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lampjuice.budgetlight.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Query(
        """
            SELECT *
            FROM budgets
            WHERE userId = :userId
            ORDER BY year DESC, month DESC
            LIMIT 1
        """,
    )
    fun observeCurrentBudget(userId: Long): Flow<BudgetEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEntity): Long

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
}
