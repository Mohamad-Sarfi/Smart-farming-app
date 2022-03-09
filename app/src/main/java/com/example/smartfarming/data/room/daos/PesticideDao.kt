package com.example.smartfarming.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartfarming.data.room.entities.PesticideEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PesticideDao {
    @Insert
    suspend fun insert(pesticideEntity: PesticideEntity)

    @Delete
    suspend fun delete(pesticideEntity: PesticideEntity)

    @Query("SELECT * FROM pesticide_table ORDER BY id DESC")
    fun getAllPesticides() : Flow<List<PesticideEntity>>

    @Query("SELECT * FROM pesticide_table WHERE garden_name = :gardenName")
    suspend fun getPesticideByGardenName(gardenName : String) : List<PesticideEntity>
}