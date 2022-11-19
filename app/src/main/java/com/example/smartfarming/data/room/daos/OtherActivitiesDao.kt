package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.OtherActivityEntity

@Dao
interface OtherActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(otherActivityEntity: OtherActivityEntity)
    
    @Query("SELECT * FROM other_activities_table WHERE gardenId = :gardenId")
    suspend fun getOtherActivitiesByGardenId(gardenId : Int) : List<OtherActivityEntity>

    @Delete
    suspend fun delete(otherActivityEntity: OtherActivityEntity)

    @Query("SELECT * FROM other_activities_table")
    suspend fun getAllOtherActivities() : List<OtherActivityEntity>

    @Query("SELECT * FROM other_activities_table WHERE gardenId = :gardenId AND time LIKE :year ORDER BY time DESC")
    suspend fun getOtherActivitiesByGardenYear(gardenId: Int, year: String) : List<OtherActivityEntity>

}