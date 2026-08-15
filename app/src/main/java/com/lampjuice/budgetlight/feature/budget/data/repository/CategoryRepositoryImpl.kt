package com.lampjuice.budgetlight.feature.budget.data.repository

import com.lampjuice.budgetlight.feature.budget.data.local.dao.CategoryDao
import com.lampjuice.budgetlight.feature.budget.data.mapper.toDomain
import com.lampjuice.budgetlight.feature.budget.data.mapper.toEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.Category
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun observeCategories(): Flow<List<Category>> {
        val categories = categoryDao.observeActiveCategories()
        return categories.map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun observeAllCategories(): Flow<List<Category>> {
        val categories = categoryDao.observeAllCategories()
        return categories.map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insert(category: Category): Long = categoryDao.insert(category.toEntity())

    override suspend fun insertAll(categories: List<Category>) {
        categoryDao.insertAll(categories.map { it.toEntity() })
    }

    override suspend fun archive(categoryId: Long) {
        categoryDao.setArchived(categoryId, true)
    }

    override suspend fun getCount(): Int = categoryDao.getCount()
}
