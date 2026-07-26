package com.lampjuice.budgetlight.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MoneyText(
    modifier: Modifier = Modifier,
    amount: Double,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Text(
        modifier = modifier,
        text = "%.2f  ₽".format(amount),
        color = color,
        style = style,
    )
}

@Preview(showBackground = true)
@Composable
private fun MoneyTextPreview() {
    MoneyText(amount = 100.0)
}
