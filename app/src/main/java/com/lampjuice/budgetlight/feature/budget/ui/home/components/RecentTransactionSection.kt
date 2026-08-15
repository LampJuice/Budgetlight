package com.lampjuice.budgetlight.feature.budget.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lampjuice.budgetlight.feature.budget.ui.home.model.TransactionUi
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun RecentTransactionSection(
    onShowAllClick: () -> Unit,
    transactions: List<TransactionUi>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Последние операции",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
            )
            TextButton(
                onClick = onShowAllClick,
            ) {
                Text(
                    text = "Все",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        transactions.forEachIndexed { index, transaction ->
            TransactionItem(transaction = transaction)

            if (index != transactions.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}
