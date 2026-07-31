package com.lampjuice.budgetlight.data.repository

import com.lampjuice.budgetlight.data.local.dao.CategoryDao
import com.lampjuice.budgetlight.data.mapper.toDomain
import com.lampjuice.budgetlight.data.mapper.toEntity
import com.lampjuice.budgetlight.domain.model.Category
import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> {
        val categories = categoryDao.observeCategories()
        return categories.map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insert(category: Category) {
        categoryDao.insert(category.toEntity())
    }

    override suspend fun insertAll(categories: List<Category>) {
        categoryDao.insertAll(categories.map { it.toEntity() })
    }

    override suspend fun getCount(): Int = categoryDao.getCount()
}
