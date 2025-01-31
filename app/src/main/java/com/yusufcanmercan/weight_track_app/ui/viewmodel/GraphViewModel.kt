package com.yusufcanmercan.weight_track_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.yusufcanmercan.weight_track_app.R
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState

class GraphViewModel : ViewModel() {
    lateinit var weights: List<Weight>
    lateinit var chartData: LineData

    fun fetchData(weightViewModel: WeightViewModel) {
        val successState = weightViewModel.weights.value as WeightUIState.Success
        weights = successState.weights
    }

    fun setupLineData(context: Context) {
        val yValues = weights.mapIndexed { index, weight ->
            Entry(index.toFloat(), weight.weight.toFloat())
        }
        val yAxisSet = LineDataSet(yValues, null)
        yAxisSet.apply {
            valueTextColor = context.getColor(R.color.primary)
            valueTextSize = 12f

            circleRadius = 5f
            circleHoleRadius = 2f
        }

        val dataSets = ArrayList<ILineDataSet>()

        dataSets.add(yAxisSet)
        chartData = LineData(dataSets)
    }
}