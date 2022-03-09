package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.Garden
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenDao {

    // Garden queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(garden: Garden)

    @Delete
    suspend fun deleteGarden(garden: Garden)

    @Query("SELECT * FROM garden_table ORDER BY name ASC")
    fun getAllGardens() : Flow<List<Garden>>

    @Query("SELECT * FROM garden_table WHERE name = :name")
    suspend fun getGardenByName(name : String) : Garden
}