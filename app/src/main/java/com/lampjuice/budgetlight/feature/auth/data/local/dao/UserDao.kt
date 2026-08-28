package com.lampjuice.budgetlight.feature.auth.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lampjuice.budgetlight.feature.auth.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query(
        """
        SELECT *
        FROM users
        WHERE login = :login
        LIMIT 1
        """,
    )
    suspend fun getUserByLogin(login: String): UserEntity?

    @Query(
        """
        SELECT *
        FROM users
        LIMIT 1
        """,
    )
    fun observeUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query(
        """
            SELECT *
            FROM users
            WHERE id = :userId
            LIMIT 1
        """,
    )
    fun observeUserById(userId: Long): Flow<UserEntity?>
}
