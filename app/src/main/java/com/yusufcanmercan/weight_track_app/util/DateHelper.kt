package com.yusufcanmercan.weight_track_app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun today(): String {
    val today = Date()
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("tr", "TR"))
    return formatter.format(today)
}