package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun GreetingSection(
    userName: String = "",
    month: String = "Июль 2026",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimens.LargeSpacing,

            ),
    ) {
        Text(
            text = "Добро пожаловать \uD83D\uDC4B $userName",
            style = MaterialTheme.typography.headlineSmall,
        )

        Text(
            text = month,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
    }
}
