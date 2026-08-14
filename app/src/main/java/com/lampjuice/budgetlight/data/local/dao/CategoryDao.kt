package com.lampjuice.budgetlight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lampjuice.budgetlight.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name")
    fun observeAllCategories(): Flow<List<CategoryEntity>>

    @Query(
        """
        SELECT *
        FROM categories
        WHERE isArchived = 0
        ORDER BY name
    """,
    )
    fun observeActiveCategories(): Flow<List<CategoryEntity>>

    @Insert
    suspend fun insert(category: CategoryEntity): Long

    @Insert
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCount(): Int

    @Query(
        """
            UPDATE categories
            SET isArchived = :isArchived
            WHERE id = :categoryId
        """,
    )
    suspend fun setArchived(categoryId: Long, isArchived: Boolean)
}
