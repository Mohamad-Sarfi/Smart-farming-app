package com.example.smartfarming.ui.tasks_notification.addtask

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val repo : GardenRepo) : ViewModel(){
    var step = mutableStateOf(0)
    val MAX_STEP = 2
    val gardensList = mutableStateOf<List<Garden>>(listOf())
    val gardenNameList = mutableStateListOf<String>()
    val selectedGarden = mutableStateOf("")

    init {
        getGardens()

    }

    private fun getGardens(){
        viewModelScope.launch {
            repo.getGardens().collect{
                gardensList.value = it
                Log.i("xxGarden1", "${it}")
                populateGardensName()
            }
        }
    }

    private fun populateGardensName(){
        for (g in gardensList.value){
            Log.i("xxGarden", "${g.name}")
            gardenNameList.add(g.name)
        }
    }

    fun increaseStep(){
        if (step.value < MAX_STEP) step.value++
    }

    fun decreaseStep(){
        if (step.value > 0) step.value--
    }
}