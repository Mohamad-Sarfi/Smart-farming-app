package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.Harvest

@Dao
interface HarvestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHarvest(harvest: Harvest)

    @Delete
    suspend fun deleteHarvest(harvest: Harvest)

    @Query("SELECT * FROM harvest_table WHERE gardenName = :gardenName AND year = :year AND type = :harvestType ORDER BY month DESC")
    suspend fun getHarvestByYearType(gardenName : String, year: String, harvestType : String) : List<Harvest>

    @Query("SELECT * FROM harvest_table WHERE gardenName = :gardenName AND type = :harvestType ORDER BY year DESC")
    suspend fun getHarvestByType(gardenName: String, harvestType: String) : List<Harvest>

    @Query("SELECT * FROM harvest_table WHERE gardenName = :gardenName AND year = :year ORDER BY month DESC")
    suspend fun getHarvestByYear(gardenName: String, year: String) : List<Harvest>

}