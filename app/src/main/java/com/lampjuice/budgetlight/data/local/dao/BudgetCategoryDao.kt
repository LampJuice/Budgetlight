package com.lampjuice.budgetlight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lampjuice.budgetlight.data.local.entity.BudgetCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetCategoryDao {
    @Query(
        """
            SELECT *
            FROM budget_categories
            WHERE budgetId = :budgetId
        """,
    )
    fun observeBudgetCategories(budgetId: Long): Flow<List<BudgetCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budgetCategory: BudgetCategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(budgetCategories: List<BudgetCategoryEntity>)
}
