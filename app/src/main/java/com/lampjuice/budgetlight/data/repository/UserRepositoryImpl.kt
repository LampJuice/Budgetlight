package com.lampjuice.budgetlight.data.repository

import com.lampjuice.budgetlight.data.local.dao.UserDao
import com.lampjuice.budgetlight.data.local.entity.UserEntity
import com.lampjuice.budgetlight.data.mapper.toDomain
import com.lampjuice.budgetlight.domain.model.User
import com.lampjuice.budgetlight.domain.repository.UserRepository
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
