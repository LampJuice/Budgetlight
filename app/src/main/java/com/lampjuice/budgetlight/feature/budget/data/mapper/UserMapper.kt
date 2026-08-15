package com.lampjuice.budgetlight.feature.budget.data.mapper

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.budget.data.local.entity.UserEntity

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
)
