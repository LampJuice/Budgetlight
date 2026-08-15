package com.lampjuice.budgetlight.feature.auth.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState())
    val state = _state.asStateFlow()

    fun onLoginChange(login: String) {
        _state.update {
            it.copy(
                login = login,
                errorMessage = null,
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
                errorMessage = null,
            )
        }
    }

    fun togglePasswordVisibility() {
        _state.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible,
            )
        }
    }

    fun login() {}

    fun register() {}

    fun forgotPassword() {}
}
