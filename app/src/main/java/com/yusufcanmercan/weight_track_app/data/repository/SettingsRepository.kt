package com.yusufcanmercan.weight_track_app.data.repository

import com.yusufcanmercan.weight_track_app.data.datastore.SettingsDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: SettingsDataStore,
) {
    suspend fun setDarkMode(enabled: Boolean) = dataStore.setDarkMode(enabled)
    val darkModeFlow = dataStore.isDarkMode
}