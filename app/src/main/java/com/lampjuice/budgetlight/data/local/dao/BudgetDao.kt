package com.lampjuice.budgetlight.data.local.dao

import androidx.room.Dao
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
}
