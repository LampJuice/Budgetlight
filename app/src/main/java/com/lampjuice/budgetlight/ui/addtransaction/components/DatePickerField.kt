package com.lampjuice.budgetlight.ui.addtransaction.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DatePickerField(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale.forLanguageTag("ru-RU"),
    )

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = date.format(formatter),
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = "Дата")
            },
            modifier = modifier
                .fillMaxWidth(),
            trailingIcon = {
                Text("📅")
            },

        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    showDatePicker = true
                },
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(
                    LocalDate.of(
                        year,
                        month + 1,
                        dayOfMonth,
                    ),
                )
                showDatePicker = false
            },
            date.year,
            date.monthValue - 1,
            date.dayOfMonth,

        ).apply {
            setOnCancelListener {
                showDatePicker = false
            }
        }.show()
    }
}
