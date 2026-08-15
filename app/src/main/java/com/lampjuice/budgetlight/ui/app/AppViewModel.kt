package com.lampjuice.budgetlight.ui.app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AppViewModel
@Inject
constructor() : ViewModel() {
    private val _state = MutableStateFlow<AppState>(AppState.Ready)

    val state = _state.asStateFlow()
}
