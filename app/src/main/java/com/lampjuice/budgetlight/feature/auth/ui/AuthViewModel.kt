package com.lampjuice.budgetlight.feature.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.feature.auth.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState())
    val state = _state.asStateFlow()

    fun onLoginChange(login: String) {
        _state.update {
            it.copy(
                login = login,
                errorMessageResId = null,
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
                errorMessageResId = null,
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

    fun login(onSuccess: () -> Unit) {
        val currentState = state.value

        if (currentState.login.isBlank() || currentState.password.isBlank()) {
            _state.update {
                it.copy(
                    errorMessageResId = R.string.empty_login_or_password,
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessageResId = null,
                )
            }

            loginUserUseCase(
                login = currentState.login,
                password = currentState.password,
            ).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    onSuccess()
                },
                onFailure = {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessageResId = R.string.invalid_login_or_password,
                        )
                    }
                },
            )
        }
    }

    fun register() {
        _state.update {
            it.copy(
                errorMessageResId = R.string.registration_not_available,
            )
        }
    }

    fun forgotPassword() {
        _state.update {
            it.copy(
                errorMessageResId = R.string.password_reset_not_available,
            )
        }
    }
}
