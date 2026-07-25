package com.lampjuice.budgetlight.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)
