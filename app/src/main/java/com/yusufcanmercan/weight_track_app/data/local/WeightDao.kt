package com.yusufcanmercan.weight_track_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yusufcanmercan.weight_track_app.data.model.Weight

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight")
    suspend fun getAllWeights(): List<Weight>

    @Insert
    suspend fun insertWeight(weight: Weight)

    @Update
    suspend fun updateWeight(weight: Weight)

    @Delete
    suspend fun deleteWeight(weight: Weight)
}