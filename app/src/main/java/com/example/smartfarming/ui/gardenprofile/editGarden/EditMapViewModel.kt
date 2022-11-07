package com.example.smartfarming.ui.gardenprofile.editGarden

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMapViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {
    val garden = mutableStateOf<Garden?>(value = null)

    fun getGarden(gardenName : String){
        viewModelScope.launch {
            garden.value = repo.getGardenByName(gardenName)
        }
    }
}