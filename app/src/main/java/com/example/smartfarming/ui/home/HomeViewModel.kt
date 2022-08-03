package com.example.smartfarming.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.Blue500
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple500
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class HomeViewModel(val repo : GardenRepo) : ViewModel() {

    val selectedActivityGroup = mutableStateOf("all")

    fun setSelectedActivityGroup(newValue : String){
        if (selectedActivityGroup.value == newValue){
            selectedActivityGroup.value = "all"
        } else {
            selectedActivityGroup.value = newValue
        }
    }


    fun getGardens() : LiveData<List<Garden>> {
        var gardensList = liveData<List<Garden>>(){}
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

}

class HomeViewModelFactory(val repo : GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

