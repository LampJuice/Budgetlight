package com.lampjuice.budgetlight.data.mapper

import com.lampjuice.budgetlight.data.local.entity.UserEntity
import com.lampjuice.budgetlight.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
    )
}
