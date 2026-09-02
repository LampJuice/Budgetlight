package com.lampjuice.budgetlight.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.reset()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.LargeSpacing),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(
                if (state.isRegisterMode) {
                    R.string.create_account
                } else {
                    R.string.welcome
                },
            ),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.size(Dimens.SmallSpacing))

        Text(
            text = stringResource(
                if (state.isRegisterMode) {
                    R.string.create_account_subtitle
                } else {
                    R.string.sign_in_your_account
                },
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.size(Dimens.ExtraLargeSpacing))

        if (state.isRegisterMode) {
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(text = stringResource(R.string.name_label))
                },
            )
            Spacer(modifier = Modifier.size(Dimens.ItemSpacing))
        }

        OutlinedTextField(
            value = state.login,
            onValueChange = viewModel::onLoginChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text(text = stringResource(R.string.login_label))
            },
        )
        Spacer(modifier = Modifier.size(Dimens.ItemSpacing))

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text(text = stringResource(R.string.password_label))
            },
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = viewModel::togglePasswordVisibility) {
                    Icon(
                        imageVector = if (state.isPasswordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = null,
                    )
                }
            },
        )

        if (state.isRegisterMode) {
            Spacer(modifier = Modifier.size(Dimens.ItemSpacing))

            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text(text = stringResource(R.string.confirm_password_label))
                },
                visualTransformation = if (state.isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

            )
        }

        state.errorMessageResId?.let { errorResId ->
            Spacer(modifier = Modifier.size(Dimens.SmallSpacing))
            Text(
                text = stringResource(errorResId),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.size(Dimens.CardCorner))

        Button(
            onClick = {
                if (state.isRegisterMode) {
                    viewModel.register(
                        onSuccess = onAuthSuccess,
                    )
                } else {
                    viewModel.login(
                        onSuccess = onAuthSuccess,
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,

        ) {
            Text(
                text = stringResource(
                    if (state.isRegisterMode) {
                        R.string.register_button
                    } else {
                        R.string.login_button
                    },
                ),
            )
        }

        TextButton(
            onClick = {
                if (!state.isRegisterMode) {
                    viewModel.showRegistration()
                } else {
                    viewModel.showLogin()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
        ) {
            Text(
                text = stringResource(
                    if (state.isRegisterMode) {
                        R.string.already_have_account
                    } else {
                        R.string.no_account
                    },
                ),
            )
        }
        if (!state.isRegisterMode) {
            TextButton(
                onClick = {
                    viewModel.forgotPassword()
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.forgot_password))
            }
        }
    }
}
