package com.yusufcanmercan.weight_track_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class WeightViewModel : ViewModel() {
    private var _weights = MutableStateFlow<WeightUIState>(WeightUIState.Loading)
    val weights: StateFlow<WeightUIState> = _weights

    fun fetchData() {
        val weights = mutableListOf<Weight>()
        repeat(50) {
            val weight = Random.nextDouble() * 100.0
            val date = "${Random.nextInt(30 + 1)}.${Random.nextInt(12) + 1}.2024"

            weights.add(Weight(weight, date))
        }
        _weights.value = WeightUIState.Success(weights)
    }
}