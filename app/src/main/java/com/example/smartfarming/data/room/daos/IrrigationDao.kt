package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.IrrigationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IrrigationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(irrigationEntity: IrrigationEntity)

    @Delete
    suspend fun delete(irrigationEntity: IrrigationEntity)

    @Query("SELECT * FROM irrigation_table ORDER BY id")
    fun getAllIrrigation() : Flow<List<IrrigationEntity>>

    @Query("SELECT * FROM irrigation_table WHERE garden_name = :gardenName")
    suspend fun getIrrigationByGardenName(gardenName: String) : List<IrrigationEntity>

}