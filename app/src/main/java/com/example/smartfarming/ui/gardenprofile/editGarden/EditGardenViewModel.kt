package com.example.smartfarming.ui.gardenprofile.editGarden

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class EditGardenViewModel(val repo: GardenRepo) : ViewModel() {



    private val garden = mutableStateOf<Garden?>(
        null
    )

    var newName = mutableStateOf("")
    var newAge = mutableStateOf(0)
    var latLong = mutableStateOf("")
    var plantType = mutableStateOf("")
    var plantVarieties = mutableStateListOf<String>()
    var soilType = mutableStateOf("")
    var irrigationType = mutableStateOf("")
    var irrigationDuration = mutableStateOf(0.0)
    var irrigationVolume = mutableStateOf(0.0)
    var area = mutableStateOf(0.0)

    fun initializeValues(){
        newName.value = garden.value!!.name
        newAge.value = garden.value!!.age
        latLong.value = garden.value!!.lat_long
        plantType.value = garden.value!!.plant_type
        plantVarieties = garden.value!!.plant_varieties.split(',').toMutableStateList()
        soilType.value = garden.value!!.soil_type
        irrigationType.value = garden.value!!.irrigation_type
        irrigationDuration.value = garden.value!!.irrigation_duration
        irrigationVolume.value = garden.value!!.irrigation_volume
        area.value = garden.value!!.area

    }

    fun addVariety(variety : String) {
        if (variety !in plantVarieties){
            plantVarieties.add(variety)
        }
    }
    fun removeVariety(variety: String){
        plantVarieties.remove(variety)
    }

    fun updateNewAge(value : String, activity : Activity){
        try {
            if (value == "") {
                newAge.value = 0
            } else {
                newAge.value = value.toInt()
            }
        } catch (e : Exception){
            Toast.makeText(activity, "عدد وارد کنید", Toast.LENGTH_SHORT).show()
        }
    }


    private fun fetchGarden(gardenName : String) {
        viewModelScope.launch {
            garden.value =  repo.getGardenByName(gardenName)

        }
    }

    fun getGarden(gardenName: String) : MutableState<Garden?> {
        fetchGarden(gardenName)
        return garden
    }



}

class EditGardenViewModelFactory(val repo: GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditGardenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditGardenViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}