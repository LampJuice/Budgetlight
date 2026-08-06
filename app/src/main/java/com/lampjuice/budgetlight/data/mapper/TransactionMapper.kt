package com.lampjuice.budgetlight.data.mapper

import com.lampjuice.budgetlight.data.local.entity.TransactionEntity
import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.model.TransactionType

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
