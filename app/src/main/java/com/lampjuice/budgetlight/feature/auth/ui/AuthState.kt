package com.lampjuice.budgetlight.feature.auth.ui

data class AuthState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessageResId: Int? = null,
    val isPasswordVisible: Boolean = false,

)
