package com.example.smartfarming.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.Blue500
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple500
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.utils.getTaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    val selectedActivityGroup = mutableStateOf("all")
    val tasksList = mutableStateListOf<Task>()
    var gardensList = liveData<List<Garden>>(){}

    init {
        getGardens()
        getAllTasks()
    }

    private fun getAllTasks(){
        viewModelScope.launch {
            repo.getAllTasks().collect{
                Log.i("TAG tasks list", "$it")

                tasksList.clear()

                for (t in it){
                    tasksList.add(t)
                }

//                for (t in getTaskList(gardensList.value ?: listOf())){
//                    tasksList.add(t)
//                }
            }
        }
    }

    fun setSelectedActivityGroup(newValue : String){
        if (selectedActivityGroup.value == newValue){
            selectedActivityGroup.value = "all"
        } else {
            selectedActivityGroup.value = newValue
        }
    }

    fun getGardens() : LiveData<List<Garden>> {
        viewModelScope.launch {
            gardensList = repo.getGardens().asLiveData()
        }

        return gardensList
    }

    fun taskColor(activityType: String) : Color {
        return when (activityType) {
            ActivityTypesEnum.FERTILIZATION.name -> Purple500
            ActivityTypesEnum.IRRIGATION.name -> Blue500
            ActivityTypesEnum.PESTICIDE.name -> YellowPesticide
            else -> MainGreen
        }
    }

    fun taskName(activityType: String) : String {
        return when (activityType) {
            ActivityTypesEnum.FERTILIZATION.name -> "تغذیه"
            ActivityTypesEnum.IRRIGATION.name -> "آبیاری"
            ActivityTypesEnum.PESTICIDE.name -> "سمپاشی"
            else -> "سایر"
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            repo.deleteTask(task)
        }
    }

}
