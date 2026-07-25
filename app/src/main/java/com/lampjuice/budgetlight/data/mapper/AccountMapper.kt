package com.lampjuice.budgetlight.data.mapper

import com.lampjuice.budgetlight.data.local.entity.AccountEntity
import com.lampjuice.budgetlight.domain.model.Account

fun AccountEntity.toDomain(): Account {
    return Account(
        id = id,
        userId = userId,
        name = name,
    )
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = id,
        userId = userId,
        name = name,
    )
}
