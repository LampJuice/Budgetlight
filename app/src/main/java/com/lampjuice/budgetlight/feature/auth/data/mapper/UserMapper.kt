package com.lampjuice.budgetlight.feature.auth.data.mapper

import com.lampjuice.budgetlight.feature.auth.data.local.entity.UserEntity
import com.lampjuice.budgetlight.feature.auth.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    login = login,
    name = name,
)
