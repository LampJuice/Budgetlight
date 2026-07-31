package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun observeCategories(): Flow<List<Category>>

    suspend fun insert(category: Category)

    suspend fun insertAll(categories: List<Category>)
}
