package com.yusufcanmercan.weight_track_app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Weight(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val weight: Double,
    val date: String,
) : Parcelable