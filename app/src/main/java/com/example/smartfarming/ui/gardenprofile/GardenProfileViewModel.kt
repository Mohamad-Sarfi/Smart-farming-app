package com.example.smartfarming.ui.gardenprofile

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.utils.initialGarden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class GardenProfileViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    private val garden = MutableLiveData<Garden>().apply {
        value = initialGarden
    }

    fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
        }
    }

    fun getGarden() : MutableLiveData<Garden> {
        return garden
    }

    var gardensList = flow<List<Garden>> {}

    fun getAllGardens() {
        viewModelScope.launch {
             gardensList = repo.getGardens()
        }
    }

}

class GardenProfileViewModelFactory(val repo: GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GardenProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GardenProfileViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}