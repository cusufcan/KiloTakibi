package com.yusufcanmercan.weight_track_app.data.repository

import com.yusufcanmercan.weight_track_app.data.local.WeightLocalSource
import com.yusufcanmercan.weight_track_app.data.model.Weight
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val weightLocalSource: WeightLocalSource,
) {
    suspend fun getAllWeights(): List<Weight> {
        return weightLocalSource.getAllWeights()
    }

    suspend fun addWeight(weight: Weight) {
        weightLocalSource.addWeight(weight)
    }

    suspend fun updateWeight(weight: Weight) {
        weightLocalSource.updateWeight(weight)
    }

    suspend fun deleteWeight(weight: Weight) {
        weightLocalSource.deleteWeight(weight)
    }
}