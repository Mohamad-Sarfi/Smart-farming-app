package com.example.smartfarming.ui.gardenprofile

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.utils.initialGarden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class GardenProfileViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {
    var tasksList = listOf<Task>()
    val gardenTasks = mutableStateListOf<Task>()
    val garden = mutableStateOf<Garden?>(null)
    var gardensList = flow<List<Garden>> {}
    val shownList = mutableStateListOf<Task>()

    fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
            getGardenTasks()
        }
    }

    fun getAllGardens() {
        viewModelScope.launch {
            gardensList = repo.getGardens()

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
}
