package com.lampjuice.budgetlight.ui.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.lampjuice.budgetlight.ui.components.scaffold.BudgetScaffold
import com.lampjuice.budgetlight.ui.home.components.TransactionItem
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.util.toTransactionDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    onBack: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val groupedTransactions = state.transactions
        .filter { state.filter.matches(it.type) }
        .sortedByDescending { it.date }
        .groupBy { it.date }

    BudgetScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Операции")
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = Dimens.ScreenPadding,
                    end = Dimens.ScreenPadding,
                ),
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                TransactionFilter.entries.forEachIndexed { index, filter ->
                    SegmentedButton(
                        selected = state.filter == filter,
                        onClick = {
                            viewModel.onFilterChanged(filter)
                        },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = TransactionFilter.entries.size,
                        ),
                    ) {
                        Text(
                            text = when (filter) {
                                TransactionFilter.ALL -> "Все"
                                TransactionFilter.EXPENSE -> "Расходы"
                                TransactionFilter.INCOME -> "Доходы"
                            },
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = Dimens.LargeSpacing,
                    bottom = paddingValues.calculateBottomPadding() + Dimens.LargeSpacing,
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
            ) {
                groupedTransactions.forEach { (date, transactions) ->
                    item(key = "header_$date") {
                        Text(
                            text = date.toTransactionDateString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    items(
                        items = transactions,
                        key = { it.id },

                    ) { transaction ->
                        TransactionItem(transaction = transaction)
                    }
                }
            }
        }
    }
}
