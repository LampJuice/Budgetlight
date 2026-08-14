package com.lampjuice.budgetlight.ui.home.components.categorysection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.domain.model.CategoryIcon
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.ui.components.segmented.BudgetSegmentedButtons
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onSave: (
        name: String,
        icon: CategoryIcon,
        type: TransactionType,
    ) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var icon by rememberSaveable { mutableStateOf(CategoryIcon.OTHER) }
    var type by rememberSaveable { mutableStateOf(TransactionType.EXPENSE) }

    val canSave = name.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.category_add),
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.Spacing),
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(
                            text = stringResource(R.string.category_name),
                        )
                    },
                    singleLine = true,

                )

                BudgetSegmentedButtons(
                    items = TransactionType.entries,
                    selectedItem = type,
                    onItemSelected = { type = it },
                    label = { type ->
                        when (type) {
                            TransactionType.EXPENSE -> stringResource(R.string.expense)
                            TransactionType.INCOME -> stringResource(R.string.income)
                        }
                    },
                )
                CategoryIconPicker(
                    selectedIcon = icon,
                    onIconSelected = { icon = it },
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = canSave,
                onClick = {
                    onSave(
                        name.trim(),
                        icon,
                        type,
                    )
                },
            ) {
                Text(
                    text = stringResource(R.string.save),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(
                    text = stringResource(R.string.cancel_button),
                )
            }
        },

    )
}
