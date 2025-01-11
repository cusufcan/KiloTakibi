package com.yusufcanmercan.weight_track_app.ui.state

import com.yusufcanmercan.weight_track_app.data.model.Weight

sealed class WeightUIState {
    data object Loading : WeightUIState()
    data class Success(val weights: List<Weight>) : WeightUIState()
    data class Error(val message: String) : WeightUIState()
}