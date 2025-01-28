package com.yusufcanmercan.weight_track_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufcanmercan.weight_track_app.core.Constants
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.data.model.WeightStat
import com.yusufcanmercan.weight_track_app.data.repository.WeightRepository
import com.yusufcanmercan.weight_track_app.ui.state.WeightUIState
import com.yusufcanmercan.weight_track_app.util.minusDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
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
        val current = weights.lastOrNull()?.weight ?: Constants.DOUBLE_ZERO
        val lastSecond = weights.dropLast(1).lastOrNull()?.weight ?: Constants.DOUBLE_ZERO
        val change = lastSecond.let { current.minus(it) }
        val weekly = calculateChangeInPeriod(weights, Constants.WEEK)
        val monthly = calculateChangeInPeriod(weights, Constants.MONTH)

        return WeightStat(current, change, weekly, monthly)
    }

    private fun calculateChangeInPeriod(weights: List<Weight>, period: Int): Double {
        if (weights.isEmpty()) return Constants.DOUBLE_ZERO

        val lastWeight = weights.last()
        val currentDate = Constants.formatter.parse(lastWeight.date)!!
        val targetDate = currentDate.minusDays(period)
        val targetDateString = Constants.formatter.format(targetDate)

        val foundWeight = weights.find { it.date == targetDateString } ?: findWeightInLastNDays(
            weights, targetDate, period
        )

        return foundWeight?.let { lastWeight.weight - it.weight } ?: Constants.DOUBLE_ZERO
    }

    private fun findWeightInLastNDays(weights: List<Weight>, targetDate: Date, days: Int): Weight? {
        for (i in 1..<days) {
            val checkDate = targetDate.minusDays(i)
            val checkDateString = Constants.formatter.format(checkDate)
            val foundWeight = weights.find { it.date == checkDateString }
            if (foundWeight != null) return foundWeight
        }
        return null
    }

    private suspend fun weights(): List<Weight> = weightRepository.getAllWeights()

    private suspend fun updateWeightData() {
        val weights = weights()
        _weightStat.value = calculateWeightStat(weights)
        _weights.value = WeightUIState.Success(weights)
    }
}