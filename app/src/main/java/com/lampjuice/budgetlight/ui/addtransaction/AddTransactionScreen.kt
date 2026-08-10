package com.lampjuice.budgetlight.ui.addtransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.ui.addtransaction.components.CategoryDropdown
import com.lampjuice.budgetlight.ui.addtransaction.components.DatePickerField
import com.lampjuice.budgetlight.ui.components.scaffold.BudgetScaffold
import com.lampjuice.budgetlight.ui.components.segmented.BudgetSegmentedButtons
import com.lampjuice.budgetlight.ui.components.topbar.BudgetTopAppBar
import com.lampjuice.budgetlight.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onBack: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isFormValid = state.title.isNotBlank() &&
        state.amount.toLongOrNull()?.let { it > 0 } == true &&
        state.selectedCategoryId != null

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AddTransactionEvent.TransactionSaved -> onBack()
            }
        }
    }

    BudgetScaffold(
        topBar = {
            BudgetTopAppBar(
                title = "Добавить операцию",
                onBack = onBack,
            )
        },

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + Dimens.LargeSpacing,
                bottom = paddingValues.calculateBottomPadding() + Dimens.LargeSpacing,
                start = Dimens.ScreenPadding,
                end = Dimens.ScreenPadding,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
        ) {
            item {
                BudgetSegmentedButtons(
                    items = TransactionType.entries.toList(),
                    selectedItem = state.type,
                    onItemSelected = viewModel::onTypeChanged,
                    label = { type ->
                        when (type) {
                            TransactionType.EXPENSE -> "Расход"
                            TransactionType.INCOME -> "Доход"
                        }
                    },

                )
            }

            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onTitleChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Зарплата",
                            color = MaterialTheme.colorScheme.outline,
                        )
                    },
                    label = {
                        Text(text = "Операция")
                    },
                    singleLine = true,
                )
            }

            item {
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = { viewModel.onAmountChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "10 000",
                            color = MaterialTheme.colorScheme.outline,
                        )
                    },
                    suffix = {
                        Text("₽")
                    },
                    label = {
                        Text(text = "Сумма")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    singleLine = true,
                )
            }
            item {
                CategoryDropdown(
                    categories = state.categories.filter { it.type == state.type },
                    selectedCategoryId = state.selectedCategoryId,
                    onCategoryChanged = viewModel::onCategoryChanged,
                )
            }

            item {
                DatePickerField(
                    date = state.date,
                    onDateSelected = viewModel::onDateChanged,
                )
            }

            item {
                Button(
                    onClick = viewModel::onSave,
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = isFormValid && !state.isSaving,

                ) {
                    Text("Добавить")
                }
            }
        }
    }
}
