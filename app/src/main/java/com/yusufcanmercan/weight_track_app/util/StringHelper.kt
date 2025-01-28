package com.yusufcanmercan.weight_track_app.util

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