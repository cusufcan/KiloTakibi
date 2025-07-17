package com.yusufcanmercan.weight_track_app.di

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.yusufcanmercan.weight_track_app.data.repository.SettingsRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate() {
        super.onCreate()
        initializeDarkMode()
        initializeLanguage()
    }

    private fun initializeDarkMode() {
        CoroutineScope(Dispatchers.Default).launch {
            val isDarkMode = settingsRepository.darkModeFlow.first()
            when (isDarkMode) {
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                null -> {
                    val currentNightMode =
                        resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    val isSystemDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES
                    settingsRepository.setDarkMode(isSystemDarkMode)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    private fun initializeLanguage() {
        CoroutineScope(Dispatchers.Default).launch {
            val language = settingsRepository.selectedLanguageFlow.first()
            if (language != null) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(language)
                )
            } else {
                val currentLanguage = resources.configuration.locales[0].language
                settingsRepository.setLanguage(currentLanguage)
            }
        }
    }
}