package com.example.smartfarming.ui.gardenprofile

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GardenProfileViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {
    var tasksList = listOf<Task>()
    val gardenTasks = mutableStateListOf<Task>()
    val garden = mutableStateOf<Garden?>(null)
    var gardensList = listOf<Garden>()
    val shownList = mutableStateListOf<Task>()
    val allGardensName = mutableListOf<String>()
    val load = mutableStateOf(true)
    var selectedTaskStatus  = mutableStateOf("")
    val irrigationPercentage = mutableStateOf(0f)

    fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
            getGardenTasks()
        }
    }

    fun getAllGardens() {
        viewModelScope.launch {
            repo.getGardens().collect{
                gardensList = it

                allGardensName.clear()
                for(g in gardensList){
                    allGardensName.add(g.title)
                }
            }
        }
    }

    fun setCurrentGarden(gardenName: String){
        for (g in gardensList){
            if (g.title == gardenName){
                garden.value = g
                break
            }
        }
    }

    fun getGardenTasks() {
        viewModelScope.launch {
            repo.getTasksForGarden(gardenIds = garden.value!!.id).collect{
                tasksList = it
                gardenTasks.clear()

                for (task in tasksList){
                    gardenTasks.add(task)
                }
            }
        }
    }

    fun filterTasks(status: String){
        val tasks = tasksList
        gardenTasks.clear()

        if (status == ""){
            for (task in tasksList){
                gardenTasks.add(task)
            }
        } else {
            for (task in tasks){
                if (task.status == status){
                    gardenTasks.add(task)
                }
            }
        }
    }

    fun setShownTaskList(status: String){
        shownList.clear()

        for (task in tasksList){
            if (task.status == status){
                shownList.add(task)
            }
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            repo.deleteTask(task)
        }
    }

    fun updateTaskStatus(taskId : Int, status : String) {
        viewModelScope.launch {
            repo.updateTaskStatus(taskId, status)
        }
    }

    fun setSelectedTaskStatus(status : String){
        if (selectedTaskStatus.value != status){
            selectedTaskStatus.value = status
            filterTasks(status)
        } else {
            selectedTaskStatus.value = ""
            filterTasks("")
        }
    }

    fun calculatePercentage(){
        getGardenTasks()
        irrigationPercentage.value = calculateIrrigationPercentage()
    }

    private fun calculateIrrigationPercentage(): Float {
        var todosCount = 0
        var doneCount = 0
        var ignoredCount = 0

        for (task in gardenTasks) {
            if (task.activityType == ActivityTypesEnum.IRRIGATION.name) {
                when (task.status) {
                    TaskStatusEnum.TODO.name -> todosCount++
                    TaskStatusEnum.DONE.name -> doneCount++
                    TaskStatusEnum.IGNORED.name -> ignoredCount++
                }
            }
        }

        return try {
            (doneCount / (gardenTasks.size)).toFloat()
        } catch (e : java.lang.Exception){
            e.printStackTrace()
            1f
        }
    }
}
