package com.lampjuice.budgetlight.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val userId: Long,
    val name: String,
    val currency: String = "RUB"
)
