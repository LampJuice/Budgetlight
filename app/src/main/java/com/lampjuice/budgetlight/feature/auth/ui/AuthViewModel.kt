package com.lampjuice.budgetlight.feature.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.feature.auth.domain.error.AuthError
import com.lampjuice.budgetlight.feature.auth.domain.error.AuthException
import com.lampjuice.budgetlight.feature.auth.domain.usecase.LoginUserUseCase
import com.lampjuice.budgetlight.feature.auth.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onLoginChange(login: String) {
        _state.update {
            it.copy(
                login = login,
                errorMessageResId = null,
            )
        }
    }

    fun onNameChange(name: String) {
        _state.update {
            it.copy(
                name = name,
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

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
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

    fun showRegistration() {
        _state.update {
            it.copy(
                isRegisterMode = true,
                errorMessageResId = null,
            )
        }
    }

    fun showLogin() {
        _state.update {
            it.copy(
                isRegisterMode = false,
                errorMessageResId = null,
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
                login = currentState.login.trim(),
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
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessageResId = error.toMessageRes(),
                        )
                    }
                },
            )
        }
    }

    fun reset() {
        _state.value = AuthState()
    }

    fun register(onSuccess: () -> Unit) {
        val currentState = state.value

        val validationError = validateRegistration(currentState)

        if (validationError != null) {
            _state.update {
                it.copy(
                    errorMessageResId = validationError,
                )
            }
        } else {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        isLoading = true,
                        errorMessageResId = null,
                    )
                }
                registerUserUseCase(
                    login = currentState.login.trim(),
                    name = currentState.name.trim(),
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
                    onFailure = { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessageResId = error.toMessageRes(),
                            )
                        }
                    },
                )
            }
        }
    }

    fun forgotPassword() {
        setError(R.string.password_recovery_unavailable)
    }

    private fun setError(messageResId: Int) {
        _state.update {
            it.copy(
                errorMessageResId = messageResId,
            )
        }
    }
}

private fun validateRegistration(state: AuthState): Int? = when {
    state.login.isBlank() ||
        state.name.isBlank() ||
        state.password.isBlank() ||
        state.confirmPassword.isBlank() -> {
        R.string.fill_all_fields
    }

    state.password != state.confirmPassword -> {
        R.string.passwords_do_not_match
    }

    else -> null
}

private fun Throwable.toMessageRes(): Int = when (val exception = this) {
    is AuthException -> when (exception.error) {
        AuthError.UserAlreadyExists -> R.string.user_already_exists
        AuthError.InvalidCredentials -> R.string.invalid_credentials
        AuthError.UserNotFound -> R.string.invalid_credentials
        AuthError.PasswordNotFound -> R.string.invalid_credentials
        AuthError.EmptyLogin -> R.string.empty_login
        AuthError.EmptyPassword -> R.string.empty_password
        AuthError.EmptyName -> R.string.empty_name
        AuthError.PasswordMismatch -> R.string.passwords_do_not_match
    }

    else -> R.string.auth_unknown_error
}
