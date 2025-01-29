package com.yusufcanmercan.weight_track_app.data.model

data class WeightStat(
    val current: Double = 0.0,
    val change: Double = 0.0,
    val weekly: Double = 0.0,
    val monthly: Double = 0.0,
)