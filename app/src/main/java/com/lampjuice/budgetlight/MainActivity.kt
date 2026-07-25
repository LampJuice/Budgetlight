package com.lampjuice.budgetlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lampjuice.budgetlight.app.BudgetApp
import com.lampjuice.budgetlight.ui.theme.BudgetLightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetLightTheme {
                BudgetApp()
            }
        }
    }
}
