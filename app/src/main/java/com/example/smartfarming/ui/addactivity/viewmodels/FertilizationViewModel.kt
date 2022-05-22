package com.example.smartfarming.ui.addactivities.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FertilizationViewModel(val repo : GardenRepo) : ViewModel() {

    private val garden = MutableLiveData<Garden>().apply {
        value = Garden(0, "", 0, "", "", "", "", "", 0.0, 0.0,
            0.0, 0)
    }
    val fertilizationType = mutableStateOf(value = "")
    val fertilizerName = mutableStateOf(value ="")
    val fertilizationDate = mutableStateOf(value = "")
    val fertilizationVolume = mutableStateOf<Float>(0f)

    fun setFertilizationType(type : String){
        fertilizationType.value = type
    }

    fun setFertilizerName(name : String){
        fertilizerName.value = name
    }

    fun setFertilizationDate(date : String){
        fertilizationDate.value = ""
    }

    fun setFertilizationVolume(volume : Float){
        fertilizationVolume.value = volume
    }



    private fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
        }
    }

    fun getGarden(gardenName : String) : MutableLiveData<Garden> {
        getGardenByName(gardenName)
        return garden
    }


}



class FertilizationViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FertilizationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FertilizationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}