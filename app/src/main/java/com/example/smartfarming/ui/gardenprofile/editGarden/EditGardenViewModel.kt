package com.example.smartfarming.ui.gardenprofile.editGarden

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.google.android.libraries.maps.model.LatLng
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
    var irrigationDuration = mutableStateOf(0)
    var irrigationVolume = mutableStateOf(0.0)
    var irrigationCycle = mutableStateOf(0)
    var area = mutableStateOf(0)
    var polygonList : List<LatLng>? = mutableListOf<LatLng>()

    fun initializeValues(){
        newName.value = garden.value!!.title
        newAge.value = garden.value!!.age
        latLong.value = garden.value!!.location.latitude.toString() + "-" +  garden.value!!.location.longitude.toString()
        plantType.value = ""
        plantVarieties = listOf<String>() as SnapshotStateList<String>
        soilType.value = garden.value!!.soilType
        irrigationType.value = ""
        irrigationDuration.value = garden.value!!.irrigationDuration
        irrigationVolume.value = garden.value!!.irrigationVolume
        irrigationCycle.value = garden.value!!.irrigationCycle
        area.value = garden.value!!.area
        polygonList = listOf()

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


    fun setIrrigationCycle(index : Int){
        irrigationCycle.value =
            when (index) {
                0 -> 7
                1 -> 10
                2 -> 20
                3 -> 30
                4 -> 40
                5 -> 50
                6 -> 60
                else -> 10
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

    fun updateGarden() {
        viewModelScope.launch {
//            repo.updateGarden(
//                Garden(
//                    id = garden.value!!.id,
//                    title = newName.value,
//                    age = newAge.value,
//                    lat_long = latLong.value,
//                    plant_type = plantType.value,
//                    soil_type = soilType.value,
//                    irrigation_cycle = irrigationCycle.value,
//                    irrigation_duration = irrigationDuration.value,
//                    irrigation_type = irrigationType.value,
//                    irrigation_volume = irrigationVolume.value,
//                    area = area.value,
//                    user_id = 0,
//                    plant_varieties = plantVarieties.joinToString(","),
//                    polygon_list = polygonList
//                )
//            )
        }
    }



}

class EditGardenViewModelFactory(val repo: GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditGardenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditGardenViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}