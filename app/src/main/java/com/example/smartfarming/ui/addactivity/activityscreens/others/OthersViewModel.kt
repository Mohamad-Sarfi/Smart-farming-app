package com.example.smartfarming.ui.addactivity.viewmodels

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

class OthersViewModel(val repo : GardenRepo) : ViewModel() {

    val activityName = mutableStateOf("")
    val step = mutableStateOf(0)
    val date = mutableStateOf(mutableMapOf("year" to "", "month" to "", "day" to ""))

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

class OthersViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OthersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OthersViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}