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

    var step = mutableStateOf(0)

    fun increaseStep(){
        if (step.value == 0) step.value++
    }
    fun decreaseStep(){
        if (step.value == 1) step.value--
    }


    private val garden = MutableLiveData<Garden>().apply {
        value = Garden(0, "", 0, "", "", "", "", "", 0.0, 0.0,
            0.0, 0)
    }
    val fertilizationType = mutableStateOf(value = "")

    val fertilizerName = mutableStateOf(mutableListOf<String>())
    val currentFertilizerName = mutableStateOf("")

    val fertilizationDate =
        mutableStateOf(value = mutableMapOf<String, String>("day" to "", "month" to "", "year" to ""))

    val fertilizationVolume = mutableStateOf<Float>(0f)

    val fertilizationWorker = mutableStateOf(1)

    fun setFertilizationType(type : String){
        fertilizationType.value = type
    }

    fun addFertilizer(name : String){
        fertilizerName.value.add(name)
    }

    fun removeFertilizer(name : String){
        fertilizerName.value.remove(name)
    }

    fun setFertilizationVolume(volume : Float){
        fertilizationVolume.value = volume
    }



    private fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
        }
    }

    fun getGarden(gardenId : Int) : MutableLiveData<Garden> {
        getGardenById(gardenId)
        return garden
    }

    fun getGardenById(gardenId: Int) {
        viewModelScope.launch {
            garden.value = repo.getGardenById(gardenId)
        }
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