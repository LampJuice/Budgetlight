package com.lampjuice.budgetlight.feature.auth.data.repository

import com.lampjuice.budgetlight.feature.auth.data.local.dao.UserDao
import com.lampjuice.budgetlight.feature.auth.data.local.entity.UserEntity
import com.lampjuice.budgetlight.feature.auth.data.mapper.toDomain
import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class UserRepositoryImpl
@Inject
constructor(
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun getUserByLogin(login: String): User? = userDao
        .getUserByLogin(login)
        ?.toDomain()

    override suspend fun createUser(
        login: String,
        name: String,
    ): User {
        val entity = UserEntity(
            login = login,
            name = name,
        )
        val id = userDao.insertUser(entity)
        return entity
            .copy(id = id)
            .toDomain()
    }

    override suspend fun getCurrentUser(): User? = userDao
        .observeUser()
        .firstOrNull()
        ?.toDomain()

    override fun observeUserById(userId: Long): Flow<User?> = userDao.observeUserById(userId)
        .map { it?.toDomain() }
}
