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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.LargeSpacing),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.welcome),
        )
        Spacer(modifier = Modifier.size(Dimens.SmallSpacing))

        Text(
            text = stringResource(R.string.sign_in_your_account),
        )

        Spacer(modifier = Modifier.size(Dimens.ExtraLargeSpacing))

        OutlinedTextField(
            value = state.login,
            onValueChange = viewModel::onLoginChange,
            modifier = Modifier.fillMaxWidth(),
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
                viewModel.login(
                    onSuccess = onAuthSuccess,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,

        ) {
            Text(text = stringResource(R.string.login_button))
        }
        TextButton(
            onClick = {
                viewModel.register()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.register_button))
        }
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
