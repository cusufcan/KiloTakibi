package com.yusufcanmercan.weight_track_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufcanmercan.weight_track_app.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {
    private val _darkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    init {
        observeDarkMode()
    }

    private fun observeDarkMode() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.darkModeFlow.collect { enabled ->
                _darkMode.value = enabled == true
            }
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setDarkMode(enabled)
        }
    }
}