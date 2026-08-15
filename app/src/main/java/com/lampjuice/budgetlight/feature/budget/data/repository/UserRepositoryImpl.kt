package com.lampjuice.budgetlight.feature.budget.data.repository

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.budget.data.local.dao.UserDao
import com.lampjuice.budgetlight.feature.budget.data.local.entity.UserEntity
import com.lampjuice.budgetlight.feature.budget.data.mapper.toDomain
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class UserRepositoryImpl
@Inject
constructor(
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun getOrCreateUser(): User {
        val existingUser =
            userDao
                .observeUser()
                .first()

        if (existingUser != null) {
            return existingUser.toDomain()
        }
        val newUser =
            UserEntity(
                name = "User",
            )
        val id = userDao.insertUser(newUser)

        return newUser
            .copy(id = id)
            .toDomain()
    }
}
