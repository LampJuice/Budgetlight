package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import jakarta.inject.Inject

class GetUserUseCase
@Inject
constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): User = userRepository.getOrCreateUser()
}
