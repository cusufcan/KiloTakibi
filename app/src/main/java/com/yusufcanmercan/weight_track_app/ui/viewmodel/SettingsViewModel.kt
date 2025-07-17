package com.yusufcanmercan.weight_track_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufcanmercan.weight_track_app.data.repository.SettingsRepository
import com.yusufcanmercan.weight_track_app.util.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {
    private val _darkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    private val _selectedLanguage = MutableStateFlow<String?>(null)
    val selectedLanguage: StateFlow<String?> = _selectedLanguage.asStateFlow()

    init {
        observeDarkMode()
        observeSelectedLanguage()
    }

    private fun observeDarkMode() {
        viewModelScope.launch {
            repository.darkModeFlow.collectLatest { enabled ->
                _darkMode.value = enabled == true
            }
        }
    }

    private fun observeSelectedLanguage() {
        viewModelScope.launch {
            repository.selectedLanguageFlow.collectLatest { selectedLanguage ->
                _selectedLanguage.value = selectedLanguage
            }
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setDarkMode(enabled)
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            AppLogger.d("setLanguage: $language")
            repository.setLanguage(language)
        }
    }
}