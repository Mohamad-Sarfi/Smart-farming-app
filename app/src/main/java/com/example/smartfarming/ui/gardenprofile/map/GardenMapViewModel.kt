package com.example.smartfarming.ui.gardenprofile.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class GardenMapViewModel(val repo : GardenRepo) : ViewModel() {

    val garden = MutableLiveData<Garden?>().apply {
        value = null
    }

    fun getGarden(gardenName: String){
        viewModelScope.launch {
            garden.value = repo.getGardenByName(gardenName)
        }
    }



}

class GardenMapViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenMapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenMapViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}