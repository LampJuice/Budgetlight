package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.repository.UserRepository
import jakarta.inject.Inject

class InitializeUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.getOrCreateUser()
    }
}
