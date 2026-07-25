package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.User
import com.lampjuice.budgetlight.domain.repository.UserRepository
import jakarta.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        return userRepository.getUser()
    }
}
