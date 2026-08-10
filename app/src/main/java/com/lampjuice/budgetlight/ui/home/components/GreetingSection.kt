package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lampjuice.budgetlight.R
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
                vertical = Dimens.ItemSpacing,

            ),
    ) {
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(
            modifier = Modifier.height(Dimens.ItemSpacing),
        )

        Text(
            text = month,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
    }
}
