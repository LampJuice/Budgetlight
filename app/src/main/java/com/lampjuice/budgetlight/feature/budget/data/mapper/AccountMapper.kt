package com.lampjuice.budgetlight.feature.budget.data.mapper

import com.lampjuice.budgetlight.feature.budget.data.local.entity.AccountEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.Account

fun AccountEntity.toDomain(): Account = Account(
    id = id,
    userId = userId,
    name = name,
)

fun Account.toEntity(): AccountEntity = AccountEntity(
    id = id,
    userId = userId,
    name = name,
)
