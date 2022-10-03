package com.example.smartfarming.ui.addactivity.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.IrrigationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class IrrigationViewModel(val repo : GardenRepo) : ViewModel() {

    // Step management *********************************************************
    var step = mutableStateOf(0)

    fun increaseStep(){
        if (step.value == 0) step.value++
    }
    fun decreaseStep(){
        if (step.value == 1) step.value--
    }




    private val garden = mutableStateOf( Garden(0, "", 0, "", "", "", "", "", 0.0, 0.0,
        0, 0.0, listOf(),0)
    )

    // Irrigation specs
    var irrigationDate =
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))


    var irrigationType =
        mutableStateOf(garden.value!!.irrigation_type)
        set(value) { field = value }

    val irrigationDuration =
        mutableStateOf(garden.value!!.irrigation_duration)

    var waterVolume =
        mutableStateOf(garden.value!!.irrigation_volume)

    var irrigationWorkers =
        mutableStateOf(1)

    private fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
        }
    }

    fun getGarden(gardenName : String) : MutableState<Garden> {
        getGardenByName(gardenName)
        return garden
    }

    fun insertIrrigationDB(){
        viewModelScope.launch {
            repo.insertIrrigation(
                IrrigationEntity(
                    0,
                    date = irrigationDate.value["year"]!! + irrigationDate.value["month"]!! + irrigationDate.value["day"]!!,
                    irrigation_duration = irrigationDuration.value,
                    irrigation_type = irrigationType.value,
                    irrigation_volume = waterVolume.value,
                    garden_name = garden.value!!.name
                )
            )
        }
    }
}

class IrrigationViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IrrigationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IrrigationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}