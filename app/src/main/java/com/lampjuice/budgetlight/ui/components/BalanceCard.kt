package com.lampjuice.budgetlight.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lampjuice.budgetlight.ui.theme.GreenIncome
import com.lampjuice.budgetlight.ui.theme.RedExpense

@Composable
fun BalanceCard(
    balance: Long,
    income: Long,
    expense: Long,
) {
    BudgetCard {
        SectionHeader(
            text = "Текущий баланс",
        )

        MoneyText(
            amount = balance,
        )
        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            Column {
                Text("↑ Доходы")
                MoneyText(
                    amount = income,
                    color = GreenIncome,
                )
            }
            Column {
                Text("↓ Расходы")
                MoneyText(
                    amount = expense,
                    color = RedExpense,
                )
            }
        }
    }
}
