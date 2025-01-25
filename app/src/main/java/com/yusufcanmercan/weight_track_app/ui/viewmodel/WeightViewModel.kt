package com.yusufcanmercan.weight_track_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.data.repository.WeightRepository
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val weightRepository: WeightRepository,
) : ViewModel() {
    private var _weights = MutableStateFlow<WeightUIState>(WeightUIState.Loading)
    val weights: StateFlow<WeightUIState> = _weights

    fun fetchData() {
        viewModelScope.launch {
            try {
                val weights = weights()
                _weights.value = WeightUIState.Success(weights)
            } catch (e: Exception) {
                _weights.value = WeightUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun addWeight(weight: Weight): Boolean {
        if (checkDateExists(weight)) return false

        weightRepository.addWeight(weight)
        updateWeightData()

        return true
    }

    fun updateWeight(weight: Weight) {
        viewModelScope.launch {
            weightRepository.updateWeight(weight)
            updateWeightData()
        }
    }

    fun deleteWeight(weight: Weight) {
        viewModelScope.launch {
            weightRepository.deleteWeight(weight)
            updateWeightData()
        }
    }

    private suspend fun weights() = weightRepository.getAllWeights()

    private suspend fun updateWeightData() {
        val weights = weights()
        _weights.value = WeightUIState.Success(weights)
    }

    private suspend fun checkDateExists(weight: Weight): Boolean {
        val oldWeights = weights()
        return oldWeights.any { it.date == weight.date }
    }
}