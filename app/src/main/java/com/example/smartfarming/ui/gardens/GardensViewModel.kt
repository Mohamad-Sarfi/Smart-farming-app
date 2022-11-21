package com.example.smartfarming.ui.gardens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class GardensViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    var gardenTasks = mutableStateOf<List<Task>>(listOf())
    var hasIrrigation = mutableStateOf(false)
    var hasFertilization = mutableStateOf(false)
    var hasPesticide = mutableStateOf(false)
    var hasOthers = mutableStateOf(false)
    val gardenIdsList = mutableStateListOf<String>()

    fun getTasks(gardenName : Int) {
        viewModelScope.launch {
            repo.getTasksForGarden(gardenName).collect{
                gardenTasks.value = it
                Log.i("TAG garden tasks", "$it")
            }
            //getGardenNames(gardenTasks.value)
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
}