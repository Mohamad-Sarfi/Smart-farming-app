package com.example.smartfarming.ui.gardenprofile

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import com.example.smartfarming.utils.initialGarden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
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
}
