package com.example.smartfarming.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartfarming.data.room.entities.OtherActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OtherActivitiesDao {
    @Insert
    suspend fun insert(otherActivityEntity: OtherActivityEntity)

    @Delete
    suspend fun delete(otherActivityEntity: OtherActivityEntity)

    @Query("SELECT * FROM other_activities_table WHERE garden_name = :gardenName")
    fun getOtherActivitiesByGardenName(gardenName : String) : Flow<List<OtherActivityEntity>>

}