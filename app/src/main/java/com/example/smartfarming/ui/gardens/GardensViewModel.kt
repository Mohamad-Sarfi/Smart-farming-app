package com.example.smartfarming.ui.gardens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class GardensViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    var gardenTasks = mutableStateListOf<Task>()
    var allTasks = listOf<Task>()
    val gardenIdsList = mutableStateListOf<String>()

    init {
        getTasks()
    }

    fun getTasks() {
        viewModelScope.launch {
            repo.getAllTasks().collect{ taskList ->
                allTasks = taskList
            }
        }
    }

    private suspend fun getGardenNames(gardenIds : List<Int>){
            gardenIds.forEach { id ->
                val name = repo.getGardenById(id).title
                gardenIdsList.add(name)
            }
    }

    fun getGardens() : LiveData<List<Garden>> {
        var gardensList = liveData<List<Garden>>(){}

       viewModelScope.launch {
           gardensList = repo.getGardens().asLiveData()
       }

        return gardensList
    }

//    private fun decideBadgeState(){
//        decideIrrigationState()
//        decideOthersState()
//        decidePesticideState()
//        decideFertilizationState()
//    }

//    private fun decideIrrigationState(){
//        for (task in gardenTasks){
//            if (task.activityType == ActivityTypesEnum.IRRIGATION.name){
//                hasIrrigation.value = true
//                break
//            }
//        }
//    }
//
//    private fun decideFertilizationState(){
//        for (task in gardenTasks){
//            if (task.activityType == ActivityTypesEnum.FERTILIZATION.name){
//                hasFertilization.value = true
//                break
//            }
//        }
//    }
//
//    private fun decidePesticideState(){
//        for (task in gardenTasks){
//            if (task.activityType == ActivityTypesEnum.PESTICIDE.name){
//                hasPesticide.value = true
//                break
//            }
//        }
//    }
//
//    private fun decideOthersState(){
//        for (task in gardenTasks){
//            if (task.activityType == ActivityTypesEnum.Other.name){
//                hasOthers.value = true
//                break
//            }
//        }
//    }
}