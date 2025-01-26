package com.yusufcanmercan.weight_track_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufcanmercan.weight_track_app.core.Constants
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.data.model.WeightStat
import com.yusufcanmercan.weight_track_app.data.repository.WeightRepository
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import com.yusufcanmercan.weight_track_app.util.minusDays
import com.yusufcanmercan.weight_track_app.util.minusMonths
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

    private val _weightStat = MutableStateFlow(WeightStat())
    val weightStat: StateFlow<WeightStat> = _weightStat

    fun fetchData() {
        viewModelScope.launch {
            try {
                val weights = weights()
                _weightStat.value = calculateWeightStat(weights)
                _weights.value = WeightUIState.Success(weights)
            } catch (e: Exception) {
                _weights.value = WeightUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun addWeight(weight: Weight): Boolean {
        val response = weightRepository.addWeight(weight)
        if (!response) return false
        updateWeightData()
        return true
    }

    suspend fun updateWeight(weight: Weight): Boolean {
        val response = weightRepository.updateWeight(weight)
        if (!response) return false
        updateWeightData()
        return true
    }

    fun deleteWeight(weight: Weight) {
        viewModelScope.launch {
            weightRepository.deleteWeight(weight)
            updateWeightData()
        }
    }
    
    private fun calculateWeightStat(weights: List<Weight>): WeightStat {
        val lastSecond = if (weights.size > 1) {
            weights[1].weight
        } else {
            0.0
        }

        val current = weights.firstOrNull()?.weight
        val change = lastSecond.let { current?.minus(it) }
        val weekly = calculateWeeklyChange(weights)
        val monthly = calculateMonthlyChange(weights)

        return WeightStat(current, change, weekly, monthly)
    }

    private fun calculateWeeklyChange(weights: List<Weight>): Double {
        if (weights.isEmpty()) return 0.0

        val lastWeight = weights.first()

        val currentDate = Constants.formatter.parse(lastWeight.date)!!
        val oneWeekAgo = currentDate.minusDays(7)
        val oneWeekAgoString = Constants.formatter.format(oneWeekAgo)
        var foundWeight = weights.find { it.date == oneWeekAgoString }

        return if (foundWeight != null) {
            lastWeight.weight - foundWeight.weight
        } else {
            for (i in 1..6) {
                val oneWeekAgoAgain = Constants.formatter.format(oneWeekAgo.minusDays(i))
                foundWeight = weights.find { it.date == oneWeekAgoAgain }
                if (foundWeight != null) break
            }
            if (foundWeight != null) {
                lastWeight.weight - foundWeight.weight
            } else {
                0.0
            }
        }
    }

    private fun calculateMonthlyChange(weights: List<Weight>): Double {
        if (weights.isEmpty()) return 0.0

        val lastWeight = weights.first()

        val currentDate = Constants.formatter.parse(lastWeight.date)!!
        val oneMonthAgo = currentDate.minusMonths(1)
        val oneMonthAgoString = Constants.formatter.format(oneMonthAgo)
        var foundWeight = weights.find { it.date == oneMonthAgoString }

        return if (foundWeight != null) {
            lastWeight.weight - foundWeight.weight
        } else {
            for (i in 1..29) {
                val oneMonthAgoAgain = Constants.formatter.format(oneMonthAgo.minusDays(i))
                foundWeight = weights.find { it.date == oneMonthAgoAgain }
                if (foundWeight != null) break
            }
            if (foundWeight != null) {
                lastWeight.weight - foundWeight.weight
            } else {
                0.0
            }
        }
    }

    private suspend fun weights(): List<Weight> = weightRepository.getAllWeights()

    private suspend fun updateWeightData() {
        val weights = weights()
        _weightStat.value = calculateWeightStat(weights)
        _weights.value = WeightUIState.Success(weights)
    }
}