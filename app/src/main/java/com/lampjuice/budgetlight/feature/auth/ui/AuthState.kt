package com.lampjuice.budgetlight.feature.auth.ui

data class AuthState(
    val login: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessageResId: Int? = null,
    val isPasswordVisible: Boolean = false,
    val isRegisterMode: Boolean = false,

)
