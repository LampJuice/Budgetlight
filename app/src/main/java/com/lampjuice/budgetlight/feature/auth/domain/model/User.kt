package com.lampjuice.budgetlight.feature.auth.domain.model

data class User(
    val id: Long,
    val login: String,
    val name: String,
)
