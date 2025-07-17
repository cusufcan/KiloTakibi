package com.yusufcanmercan.weight_track_app.util.helper

import android.content.Context
import com.yusufcanmercan.weight_track_app.R
import java.math.RoundingMode

fun Double?.formatWeight(context: Context): String {
    val weightBigDecimal = this?.toBigDecimal()
    val weightScaled = weightBigDecimal?.setScale(2, RoundingMode.DOWN)
    val weightStripped = weightScaled?.stripTrailingZeros()
    val weightString = weightStripped?.toPlainString()
    return context.getString(R.string.weight_string, weightString)
}

fun String.toISO639(): String {
    return when (this) {
        "Türkçe" -> "tr"
        "English" -> "en"
        else -> "en"
    }
}

fun String.toLanguage(): String {
    return when (this) {
        "tr" -> "Türkçe"
        "en" -> "English"
        else -> "English"
    }
}