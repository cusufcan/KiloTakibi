package com.yusufcanmercan.weight_track_app.data.local

import com.yusufcanmercan.weight_track_app.data.model.Weight
import javax.inject.Inject

class WeightLocalSource @Inject constructor(
    private val weightDao: WeightDao,
) {
    suspend fun getAllWeights(): List<Weight> {
        return weightDao.getAllWeights()
    }

    suspend fun addWeight(weight: Weight) {
        weightDao.insertWeight(weight)
    }

    suspend fun updateWeight(weight: Weight) {
        weightDao.updateWeight(weight)
    }

    suspend fun deleteWeight(weight: Weight) {
        weightDao.deleteWeight(weight)
    }
}