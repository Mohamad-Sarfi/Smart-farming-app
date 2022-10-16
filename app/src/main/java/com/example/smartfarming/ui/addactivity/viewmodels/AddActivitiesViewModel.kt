package com.example.smartfarming.ui.addactivities.viewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.AppScreensEnum
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddActivitiesViewModel(val repo : GardenRepo) : ViewModel() {

    var gardenListState = mutableStateOf<List<Garden>?>(null)

    init {
        getGardens()
    }

    fun getGardens(){
        viewModelScope.launch() {
            repo.getGardens().collect{
                gardenListState.value = it
            }
        }
    }
}

class AddActivitiesViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddActivitiesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddActivitiesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}