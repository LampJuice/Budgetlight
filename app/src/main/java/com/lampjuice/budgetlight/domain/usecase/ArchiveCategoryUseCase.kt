package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.repository.CategoryRepository
import jakarta.inject.Inject

class ArchiveCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(categoryId: Long) {
        categoryRepository.archive(categoryId)
    }
}
