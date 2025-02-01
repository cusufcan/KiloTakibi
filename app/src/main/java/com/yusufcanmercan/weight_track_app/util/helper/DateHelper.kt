package com.yusufcanmercan.weight_track_app.util.helper

import com.yusufcanmercan.weight_track_app.core.Constants
import java.util.Calendar
import java.util.Date

fun convertToDate(dateString: String): Date {
    val formatter = Constants.formatter
    val date = formatter.parse(dateString)
    return date ?: throw Exception("Invalid date format")
}

fun Date.minusDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    calendar.add(Calendar.DAY_OF_MONTH, -days)
    return calendar.time
}