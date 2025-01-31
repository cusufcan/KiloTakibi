package com.yusufcanmercan.weight_track_app.util.graph

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.LineData
import com.yusufcanmercan.weight_track_app.R

class CustomLineChart(
    private val context: Context,
    attributeSet: AttributeSet,
) : LineChart(context, attributeSet) {
    init {
        setupChartSettings()
    }

    private fun setupChartSettings() {
        isDragEnabled = true
        legend.isEnabled = false
        description.isEnabled = false
        axisRight.isEnabled = false
    }

    fun setupChartData(data: LineData) {
        this.data = data
        setVisibleXRangeMaximum(3f)
        moveViewToX(data.xMax)
    }

    fun setupChartAxises(values: Array<String>) {
        xAxis.apply {
            valueFormatter = DateValueFormatter(values)
            granularity = 1f
            position = XAxisPosition.BOTTOM
            setDrawGridLines(false)
            textColor = context.getColor(R.color.primary)
        }

        axisLeft.apply {
            textColor = context.getColor(R.color.primary)
            gridColor = context.getColor(R.color.primary)
        }
    }
}