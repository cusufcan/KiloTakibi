package com.yusufcanmercan.weight_track_app.di

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
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
    }

    private fun initializeDarkMode() {
        CoroutineScope(Dispatchers.Default).launch {
            val isDarkMode = settingsRepository.darkModeFlow.first()
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}