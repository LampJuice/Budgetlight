package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Category
import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(): Flow<List<Category>> = categoryRepository.observeCategories()
}
