package com.yusufcanmercan.weight_track_app.util.graph

import com.github.mikephil.charting.formatter.ValueFormatter
import com.yusufcanmercan.weight_track_app.core.Constants

class DateValueFormatter(private val dates: Array<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val date = dates[value.toInt()]
        val formattedDate = Constants.formatter.parse(date)!!
        val formattedString = Constants.formatterGraph.format(formattedDate)
        return formattedString
    }
}