package com.example.smartfarming.ui.gardens

import android.util.Log
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class GardensViewModel(val repo : GardenRepo) : ViewModel() {

    var gardenTasks = liveData<List<Task>>{}

    fun getTasks(gardenName : String) {
        viewModelScope.launch {
            gardenTasks = repo.getTasksForGarden(gardenName).asLiveData()
        }
    }

    fun getGardens() : LiveData<List<Garden>> {
        var gardensList = liveData<List<Garden>>(){}
       viewModelScope.launch {
           gardensList = repo.getGardens().asLiveData()
       }

        Log.i("DBG", "${gardensList.value}")

        return gardensList
    }

}

class GardensViewModelFactory(private val repo: GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardensViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardensViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}