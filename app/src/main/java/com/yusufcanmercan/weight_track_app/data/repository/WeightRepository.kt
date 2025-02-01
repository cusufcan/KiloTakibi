package com.yusufcanmercan.weight_track_app.data.repository

import com.yusufcanmercan.weight_track_app.data.local.WeightLocalSource
import com.yusufcanmercan.weight_track_app.data.model.Weight
import com.yusufcanmercan.weight_track_app.util.helper.convertToDate
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val weightLocalSource: WeightLocalSource,
) {
    suspend fun getAllWeights(): List<Weight> {
        return weightLocalSource.getAllWeights().sortedBy { convertToDate(it.date) }
    }

    suspend fun addWeight(weight: Weight): Boolean {
        val weights = getAllWeights()
        if (weights.any { it.date == weight.date }) return false
        weightLocalSource.addWeight(weight)
        return true
    }

    suspend fun updateWeight(weight: Weight): Boolean {
        val weights = getAllWeights()
        if (weights.any { it.date == weight.date && it.id != weight.id }) return false
        weightLocalSource.updateWeight(weight)
        return true
    }

    suspend fun deleteWeight(weight: Weight) {
        weightLocalSource.deleteWeight(weight)
    }
}