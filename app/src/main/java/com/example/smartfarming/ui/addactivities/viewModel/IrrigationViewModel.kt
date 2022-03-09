package com.example.smartfarming.ui.addactivities.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.IrrigationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class IrrigationViewModel(val repo : GardenRepo) : ViewModel() {

    private val garden = MutableLiveData<Garden>().apply {
        value = Garden(0, "", 0, "", "", "", "", "", 0.0, 0.0,
            0.0, 0)
    }

    // Irrigation specs
    var irrigationDate =
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))

    var irrigationType =
        mutableStateOf(garden.value!!.irrigation_type)
    val irrigationDuration =
        mutableStateOf(garden.value!!.irrigation_duration)
    var waterVolume = garden.value!!.irrigation_volume

    private fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
        }
    }

    fun getGarden(gardenName : String) : MutableLiveData<Garden> {
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
                    irrigation_volume = waterVolume,
                    garden_name = garden.value!!.name
                )
            )
        }
    }
}

class IrrigationViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IrrigationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IrrigationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}