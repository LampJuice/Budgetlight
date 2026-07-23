package com.lampjuice.budgetlight.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lampjuice.budgetlight.ui.theme.AppShapes
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun BudgetCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),

        shape = AppShapes.medium,

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardElevation
        )

    ) {
        Column(
            modifier = Modifier.padding(Dimens.CardPadding),
            content = content
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun BudgetCardPreview() {
    BudgetCard {
        SectionHeader(
            text = "Баланс"
        )

        MoneyText(
            amount = 42580.0
        )
    }
}