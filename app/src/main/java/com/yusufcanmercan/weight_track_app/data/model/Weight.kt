package com.yusufcanmercan.weight_track_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weight(
    val weight: Double,
    val date: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}