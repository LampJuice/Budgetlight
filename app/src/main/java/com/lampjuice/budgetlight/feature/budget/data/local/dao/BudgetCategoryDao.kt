package com.lampjuice.budgetlight.feature.budget.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.lampjuice.budgetlight.feature.budget.data.local.entity.BudgetCategoryEntity
import com.lampjuice.budgetlight.feature.budget.data.local.entity.BudgetCategoryWithCategoryEntity
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

    @Transaction
    @Query(
        """
            SELECT *
            FROM budget_categories
            WHERE budgetId = :budgetId
        """,
    )
    fun observeBudgetCategoriesWithCategory(
        budgetId: Long,
    ): Flow<List<BudgetCategoryWithCategoryEntity>>

    @Query(
        """
            UPDATE budget_categories
            SET plannedAmount = :plannedAmount
            WHERE budgetId = :budgetId
            AND categoryId = :categoryId
        """,
    )
    suspend fun updatePlannedAmount(
        budgetId: Long,
        categoryId: Long,
        plannedAmount: Long,
    ): Int
}
