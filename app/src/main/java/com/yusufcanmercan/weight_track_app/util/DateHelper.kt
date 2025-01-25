package com.yusufcanmercan.weight_track_app.util

import com.yusufcanmercan.weight_track_app.core.Constants
import java.util.Date

fun today(): String {
    val today = Date()
    val formatter = Constants.formatter
    return formatter.format(today)
}

fun convertToDate(dateString: String): Date {
    val formatter = Constants.formatter
    val date = formatter.parse(dateString)
    return date ?: throw Exception("Invalid date format")
}