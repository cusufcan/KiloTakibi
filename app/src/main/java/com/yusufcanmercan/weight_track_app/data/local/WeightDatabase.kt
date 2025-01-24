package com.yusufcanmercan.weight_track_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yusufcanmercan.weight_track_app.data.model.Weight

@Database(
    entities = [Weight::class],
    version = 1,
)
abstract class WeightDatabase : RoomDatabase() {
    abstract fun getWeightDao(): WeightDao
}