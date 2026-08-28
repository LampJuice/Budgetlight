package com.lampjuice.budgetlight.feature.budget.data.mapper

import com.lampjuice.budgetlight.feature.budget.data.local.entity.TransactionEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.Transaction
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    accountId = accountId,
    title = title,
    amount = amount,
    categoryId = categoryId,
    date = date,
    type = TransactionType.valueOf(type),
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    accountId = accountId,
    title = title,
    amount = amount,
    categoryId = categoryId,
    date = date,
    type = type.name,
)
