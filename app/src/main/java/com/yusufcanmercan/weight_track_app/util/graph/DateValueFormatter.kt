package com.yusufcanmercan.weight_track_app.util.graph

import com.github.mikephil.charting.formatter.ValueFormatter
import com.yusufcanmercan.weight_track_app.util.helper.toDate
import com.yusufcanmercan.weight_track_app.util.helper.toDateStr

class DateValueFormatter(private val dates: Array<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        if (value < 0 || value >= dates.size) return ""
        val date = dates[value.toInt()]
        val formattedDate = date.toDate()!!
        val formattedString = formattedDate.time.toDateStr()
        return formattedString
    }
}