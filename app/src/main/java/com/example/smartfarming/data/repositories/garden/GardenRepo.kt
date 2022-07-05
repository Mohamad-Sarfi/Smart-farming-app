package com.example.smartfarming.data.repositories.garden

import androidx.annotation.WorkerThread
import com.example.smartfarming.data.room.daos.GardenDao
import com.example.smartfarming.data.room.daos.HarvestDao
import com.example.smartfarming.data.room.daos.IrrigationDao
import com.example.smartfarming.data.room.daos.TaskDao
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Harvest
import com.example.smartfarming.data.room.entities.IrrigationEntity
import com.example.smartfarming.data.room.entities.Task
import kotlinx.coroutines.flow.Flow
import java.time.Year

class GardenRepo(
    private val gardenDao : GardenDao,
    private val taskDao : TaskDao,
    private val irrigationDao : IrrigationDao,
    private val harvestDao: HarvestDao
) {

    // Garden repo
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGardens() : Flow<List<Garden>> {
        return gardenDao.getAllGardens()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGarden(garden: Garden){
        gardenDao.insert(garden)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getGardenByName(gardenName : String) : Garden {
        return gardenDao.getGardenByName(gardenName)
    }

    // Task repo
    @WorkerThread
    suspend fun insertTask(task : Task){
        taskDao.insert(task)
    }

    @WorkerThread
    suspend fun getTasksForGarden(gardenName: String) : Flow<List<Task>>{
        return taskDao.getTasksForGarden(gardenName)
    }

    @WorkerThread
    suspend fun getAllTasksForGarden() :  Flow<List<Task>>{
        return taskDao.getAllTasks()
    }

    // Irrigation repo
    @WorkerThread
    suspend fun insertIrrigation(irrigationEntity: IrrigationEntity){
        irrigationDao.insert(irrigationEntity)
    }

    @WorkerThread
    suspend fun getIrrigationByGardenName(gardenName : String){
        irrigationDao.getIrrigationByGardenName(gardenName)
    }

    // Harvest
    @WorkerThread
    suspend fun getHarvestByYear(gardenName: String, year: String) : List<Harvest> {
        return harvestDao.getHarvestByYear(gardenName, year)
    }

    @WorkerThread
    suspend fun getHarvestByType(gardenName: String, harvestType: String) : List<Harvest> {
        return harvestDao.getHarvestByType(gardenName, harvestType)
    }

    @WorkerThread
    suspend fun getHarvestByYearType(gardenName: String, year: String, harvestType: String) : List<Harvest> {
        return harvestDao.getHarvestByYearType(gardenName, year, harvestType)
    }


}
