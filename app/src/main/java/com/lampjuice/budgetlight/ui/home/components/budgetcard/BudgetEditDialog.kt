package com.lampjuice.budgetlight.ui.home.components.budgetcard

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.lampjuice.budgetlight.R

@Composable
fun BudgetEditDialog(
    currentLimit: Long,
    onDismiss: () -> Unit,
    onSave: (Long) -> Unit,
) {
    var value by rememberSaveable {
        mutableStateOf(
            if (currentLimit == 0L) {
                ""
            } else {
                currentLimit.toString()
            },
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(R.string.budget_of_month),
            )
        },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        value = newValue
                    }
                },
                label = {
                    Text(
                        stringResource(R.string.limit_for_month),
                    )
                },
                singleLine = true,
                suffix = {
                    Text(
                        stringResource(R.string.currency_rub),
                    )
                },
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val limit = value.toLongOrNull()

                    if (limit != null && limit > 0L) {
                        onSave(limit)
                    }
                },
            ) {
                Text(
                    stringResource(R.string.save),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(
                    stringResource(R.string.cancel_button),
                )
            }
        },

    )
}
