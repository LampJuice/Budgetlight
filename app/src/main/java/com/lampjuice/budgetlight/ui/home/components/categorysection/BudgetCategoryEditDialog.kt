package com.lampjuice.budgetlight.ui.home.components.categorysection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
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
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun BudgetCategoryEditDialog(
    category: CategoryBudgetUi,
    onDismiss: () -> Unit,
    onSave: (Long) -> Unit,
    onArchive: () -> Unit,
) {
    var showArchiveConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    var value by rememberSaveable {
        mutableStateOf(
            if (category.limit == 0L) {
                ""
            } else {
                category.limit.toString()
            },
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(category.title)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
            ) {
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
                TextButton(
                    onClick = {
                        showArchiveConfirmation = true
                    },
                ) {
                    Text(
                        text = stringResource(R.string.category_archive),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
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

    if (showArchiveConfirmation) {
        AlertDialog(
            onDismissRequest = {
                showArchiveConfirmation = false
            },
            title = {
                Text(
                    text = stringResource(R.string.category_archive),
                )
            },
            text = {
                Text(
                    text = stringResource(
                        R.string.category_archive_confirmation,
                        category.title,
                    ),
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showArchiveConfirmation = false
                        onArchive()
                    },
                ) {
                    Text(
                        text = stringResource(R.string.category_archive),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showArchiveConfirmation = false
                    },
                ) {
                    Text(
                        text = stringResource(R.string.cancel_button),
                    )
                }
            },
        )
    }
}
