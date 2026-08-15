package com.lampjuice.budgetlight.feature.auth.data.mapper

import com.lampjuice.budgetlight.feature.auth.data.local.entity.UserEntity
import com.lampjuice.budgetlight.feature.auth.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
)
