package com.lampjuice.budgetlight.ui.addtransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.ui.addtransaction.components.CategoryDropdown
import com.lampjuice.budgetlight.ui.addtransaction.components.DatePickerField
import com.lampjuice.budgetlight.ui.components.scaffold.BudgetScaffold
import com.lampjuice.budgetlight.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onBack: () -> Unit,
    viewModel: AddTransactionViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BudgetScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Добавить операцию")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                        )
                    }
                },
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
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TransactionType.entries.forEachIndexed { index, type ->
                        SegmentedButton(
                            selected = state.type == type,
                            onClick = { viewModel.onTypeChanged(type) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = TransactionType.entries.size,
                            ),
                        ) {
                            Text(
                                text = when (type) {
                                    TransactionType.EXPENSE -> "Расход"
                                    TransactionType.INCOME -> "Доход"
                                },
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onTitleChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Зарплата")
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
                        Text(text = "10000")
                    },
                    label = {
                        Text(text = "Сумма")
                    },
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
                    enabled = !state.isSaving,

                ) {
                    Text("Добавить")
                }
            }
        }
    }
}
