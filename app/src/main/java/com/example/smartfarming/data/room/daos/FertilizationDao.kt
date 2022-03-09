package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.FertilizationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FertilizationDao {
    // Fertilization queries
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFertilization(fertilizationEntity : FertilizationEntity)

    @Query("SELECT * FROM fertilization_table ORDER BY id ASC")
    fun getAllFertilization() : Flow<List<FertilizationEntity>>

    @Query("SELECT * FROM fertilization_table WHERE garden_name = :gardenName")
    fun getFertilizationByGardenName(gardenName : String) : Flow<List<FertilizationEntity>>

    @Delete
    suspend fun deleteFertilization(fertilizationEntity: FertilizationEntity)
}