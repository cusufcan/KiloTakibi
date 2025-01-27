package com.yusufcanmercan.weight_track_app.core

import java.text.SimpleDateFormat
import java.util.Locale

object Constants {
    val localeEn: Locale = Locale.US
    private val localeTr = Locale("tr", "TR")
    val formatter = SimpleDateFormat("dd MMMM yyyy", localeTr)

    const val DOUBLE_ZERO = 0.0

    const val WEEK = 7
    const val MONTH = 30
}