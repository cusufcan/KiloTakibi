package com.yusufcanmercan.weight_track_app.util.helper

import androidx.appcompat.app.AppCompatDelegate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toDateStr(): String {
    val locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
    val formatter = SimpleDateFormat("dd MMMM yyyy", locale)
    return formatter.format(Date(this))
}

fun String.toDate(): Date? {
    val locale = AppCompatDelegate.getApplicationLocales()[0] ?: Locale.getDefault()
    val formatter = SimpleDateFormat("dd MMMM yyyy", locale)
    return formatter.parse(this)
}

fun Date.minusDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    calendar.add(Calendar.DAY_OF_MONTH, -days)
    return calendar.time
}