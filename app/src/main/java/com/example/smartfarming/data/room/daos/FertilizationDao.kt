package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.FertilizationEntity
import kotlinx.coroutines.flow.Flow
import java.time.Year

@Dao
interface FertilizationDao {
    // Fertilization queries
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFertilization(fertilizationEntity : FertilizationEntity)

    @Query("SELECT * FROM fertilization_table ORDER BY id ASC")
    fun getAllFertilization() : Flow<List<FertilizationEntity>>

    @Query("SELECT * FROM fertilization_table WHERE gardenId = :gardenId")
    fun getFertilizationByGardenName(gardenId : Int) : Flow<List<FertilizationEntity>>

    @Delete
    suspend fun deleteFertilization(fertilizationEntity: FertilizationEntity)

    @Query("SELECT * FROM fertilization_table WHERE gardenId = :gardenId AND time LIKE :year")
    suspend fun getFertilizationByGardenNameYear(gardenId: Int, year: String) : List<FertilizationEntity>
}