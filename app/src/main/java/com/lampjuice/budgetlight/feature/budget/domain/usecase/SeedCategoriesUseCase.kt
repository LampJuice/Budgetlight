package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.domain.defaultdata.DefaultCategories
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import jakarta.inject.Inject

class SeedCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke() {
        if (categoryRepository.getCount() > 0) {
            return
        }
        categoryRepository.insertAll(DefaultCategories.list)
    }
}
