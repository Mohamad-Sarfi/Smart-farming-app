package com.example.smartfarming.ui.addgarden

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddGardenViewModel(val repo : GardenRepo) : ViewModel() {
    val MAX_STEPS = 4

    val typeArray = mutableStateOf(listOf<String>())
    var gardenName = mutableStateOf("")

    var gardenAge = mutableStateOf<Int>(0)

    var location = MutableLiveData<Map<String, String>>().apply {
        value = mutableMapOf("lat" to "0", "long" to "0")
    }

    var polygonPath = mutableListOf<LatLng>()

    var irrigationDuration = mutableStateOf("")

    var irrigationCycle = mutableStateOf(0)
    var irrigationVolume = mutableStateOf("")

    var step = mutableStateOf(1)

    var isLocationSet = mutableStateOf(false)

    private var locationList = MutableLiveData<List<LatLng>>().apply {
        value = listOf()
    }

    val gardenArea = mutableStateOf(0.0)

    var soilType = MutableLiveData<String>().apply {
        value = ""
    }


    fun incrementStep(){
        if (step.value!! < MAX_STEPS){
            step.value = step.value!! + 1
        }
    }

    fun decrementStep(){
        if (step.value!! > 1){
            step.value = step.value!! - 1
        }
    }


    fun addType(newType : String){
        var array : List<String>? = typeArray.value
        if (newType !in array!! && array.size < 5){
                array = array + listOf(newType)
        }
        typeArray.value = array
    }

    fun removeFromTypeArray(item : String){
        val index = typeArray.value!!.indexOf(item)
        val tempArray = typeArray.value!!.toMutableList().also {
            it.removeAt(index)
        }
        typeArray.value = tempArray
    }


    fun setLocationList(locations : List<LatLng>){
        locationList.value = locations
        location.value = mutableMapOf("lat" to locations[0].latitude.toString().substring(0,6), "long" to locations[0].longitude.toString().substring(0,6))
    }

    fun setIrrigationCycle(index : Int){
        irrigationCycle.value =
            when (index){
                0 -> 7
                1 -> 10
                2 -> 20
                3 -> 30
                4 -> 40
                5 -> 50
                6 -> 60
                else -> 7
        }
    }

    // Button click handler

    fun checkFirstStep() : Boolean{
        return gardenName.value != "" && gardenAge.value != 0 && !typeArray.value.isNullOrEmpty()
    }

    fun checkSecondStep() : Boolean {
        return irrigationDuration.value != "" && irrigationCycle.value != 0 && irrigationVolume.value != ""
    }

    fun checkThirdStep() : Boolean {
        return isLocationSet.value && gardenArea.value != 0.0
    }


    // Database
    fun addGardenToDb(garden : Garden){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertGarden(garden)
        }
    }

    fun getGardens() : LiveData<List<Garden>>{
        var list = liveData<List<Garden>> {  }
        viewModelScope.launch(Dispatchers.IO) {
            list = repo.getGardens().asLiveData()
        }
        return list
    }

    fun decideIconSource(step : Int) : ImageVector {
        return when(step) {
            1 -> Icons.Default.Eco
            2 -> Icons.Default.WaterDrop
            3 -> Icons.Default.Public
            4 -> Icons.Default.Science
            else -> Icons.Default.Eco
        }
    }

}

class AddGardenViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddGardenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddGardenViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}