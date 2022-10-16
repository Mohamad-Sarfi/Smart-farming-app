package com.example.smartfarming.data.repositories.garden

import androidx.annotation.WorkerThread
import com.example.smartfarming.data.room.daos.*
import com.example.smartfarming.data.room.entities.*
import kotlinx.coroutines.flow.Flow
import java.time.Year
import javax.inject.Inject

class GardenRepo @Inject constructor(
    private val gardenDao : GardenDao,
    private val taskDao : TaskDao,
    private val irrigationDao : IrrigationDao,
    private val harvestDao: HarvestDao,
    private val fertilizationDao: FertilizationDao,
    private val pesticideDao: PesticideDao
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

    @WorkerThread
    suspend fun getGardenById(gardenId : Int) : Garden {
        return gardenDao.getGardenById(gardenId)
    }

    @WorkerThread
    suspend fun updateGarden(garden: Garden) {
        gardenDao.updateGarden(garden)
    }

    // Task repo
    @WorkerThread
    suspend fun insertTask(task : Task){
        taskDao.insert(task)
    }

    @WorkerThread
    fun getTasksForGarden(gardenName: String) : Flow<List<Task>>{
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
    suspend fun getIrrigationByGardenName(gardenName : String) : List<IrrigationEntity> {
        return irrigationDao.getIrrigationByGardenName(gardenName)
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

    @WorkerThread
    suspend fun insertHarvest(harvest: Harvest) {
        harvestDao.insertHarvest(harvest)
    }

    @WorkerThread
    suspend fun deleteHarvest(harvest: Harvest){
        harvestDao.deleteHarvest(harvest)
    }

    @WorkerThread
    suspend fun getHarvestByGardenName(gardenName : String) : Flow<List<Harvest>>{
        return harvestDao.getHarvestByGarden(gardenName)
    }


    //Fertilization
    @WorkerThread
    suspend fun insertFertilization(fertilizationEntity: FertilizationEntity) {
        fertilizationDao.insertFertilization(fertilizationEntity)
    }

    @WorkerThread
    fun getFertilizationByGardenName(gardenName: String) : Flow<List<FertilizationEntity>>{
        return fertilizationDao.getFertilizationByGardenName(gardenName)
    }

    @WorkerThread
    suspend fun deleteFertilization(fertilizationEntity: FertilizationEntity){
        fertilizationDao.deleteFertilization(fertilizationEntity)
    }

    //Pesticide
    @WorkerThread
    fun getPesticidesByGardenName(gardenName: String) : Flow<List<PesticideEntity>>{
        return pesticideDao.getPesticideByGardenName(gardenName)
    }

}
