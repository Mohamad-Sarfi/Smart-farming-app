package com.example.smartfarming.di

import android.content.Context
import androidx.room.Room
import com.example.smartfarming.data.room.GardenDb
import com.example.smartfarming.data.room.daos.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext : Context) : GardenDb {
        return Room.databaseBuilder(
            appContext,
            GardenDb::class.java,
            "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGardenDao(gardenDb: GardenDb) : GardenDao {
        return gardenDb.gardenDao()
    }

    @Provides
    fun provideFertilizationDao(gardenDb: GardenDb) : FertilizationDao = gardenDb.fertilizationDao()

    @Provides
    fun provideHarvestDao(gardenDb: GardenDb) : HarvestDao = gardenDb.harvestDao()

    @Provides
    fun provideIrrigationDao(gardenDb: GardenDb) : IrrigationDao = gardenDb.irrigationDao()

    @Provides
    fun providePesticideDao(gardenDb: GardenDb) : PesticideDao = gardenDb.pesticideDao()

    @Provides
    fun provideTaskDao(gardenDb: GardenDb) : TaskDao = gardenDb.taskDao()

    @Provides
    fun provideOtherActivitiesDao(gardenDb: GardenDb) : OtherActivitiesDao = gardenDb.otherActivitiesDao()

}