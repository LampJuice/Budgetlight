package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.theme.GreenIncome

@Composable
fun BudgetSummaryCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.CardCorner),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),

    ) {
        Column(
            modifier = Modifier.padding(Dimens.LargeSpacing),
            verticalArrangement = Arrangement.spacedBy(Dimens.CardCorner),
        ) {
            Text(
                text = "Бюджет месяца",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "150 000 P",
                style = MaterialTheme.typography.headlineLarge,
            )

            LinearProgressIndicator(
                progress = { 0.55f },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.LargeSpacing),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
            ) {
                BudgetValueRow(
                    title = "Потрачено",
                    value = "83 300 P",
                    color = MaterialTheme.colorScheme.onSurface,
                )
                BudgetValueRow(
                    title = "Осталось",
                    value = "67 000 P",
                    color = GreenIncome,
                )
            }
        }
    }
}
