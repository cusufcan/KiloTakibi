package com.yusufcanmercan.weight_track_app.di

import android.content.Context
import androidx.room.Room
import com.yusufcanmercan.weight_track_app.data.local.WeightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideWeightDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, WeightDatabase::class.java, "weight_database",
    ).build()

    @Singleton
    @Provides
    fun provideWeightDao(weightDatabase: WeightDatabase) = weightDatabase.getWeightDao()
}