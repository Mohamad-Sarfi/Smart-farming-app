package com.example.smartfarming.ui.home

import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class HomeViewModel(val repo : GardenRepo) : ViewModel() {

    fun getGardens() : LiveData<List<Garden>> {
        var gardensList = liveData<List<Garden>>(){}
        viewModelScope.launch {
            gardensList = repo.getGardens().asLiveData()
        }

        return gardensList
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

