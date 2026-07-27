package com.lampjuice.budgetlight.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.ui.components.BudgetCard
import com.lampjuice.budgetlight.ui.components.MoneyText
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.theme.GreenIncome
import com.lampjuice.budgetlight.ui.theme.RedExpense

@Composable
fun TransactionItem(
    transaction: TransactionUi,
) {
    val amountColor = when (transaction.type) {
        TransactionType.INCOME -> GreenIncome
        TransactionType.EXPENSE -> RedExpense
    }

    val amountPrefix = when (transaction.type) {
        TransactionType.INCOME -> "+"
        TransactionType.EXPENSE -> "-"
    }
    BudgetCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null,
            )
            Spacer(
                modifier = Modifier.width(Dimens.ItemSpacing),
            )
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = transaction.date,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            MoneyText(
                amount = transaction.amount,
                prefix = amountPrefix,
                color = amountColor,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionItemPreview(
    transaction: TransactionUi = TransactionUi(
        title = "Перевод",
        amount = 7505,
        date = "12.02.2023",
        type = TransactionType.INCOME,
    ),
) {
    TransactionItem(transaction = transaction)
}
