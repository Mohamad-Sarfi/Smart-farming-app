package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.OtherActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OtherActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(otherActivityEntity: OtherActivityEntity)
    
    @Query("SELECT * FROM other_activities_table WHERE garden_name = :gardenName")
    suspend fun getOtherActivitiesByGardenName(gardenName : String) : List<OtherActivityEntity>

    @Delete
    suspend fun delete(otherActivityEntity: OtherActivityEntity)


    @Query("SELECT * FROM other_activities_table")
    suspend fun getAllOtherActivities() : List<OtherActivityEntity>

}