package com.example.smartfarming.ui.addactivity.activityscreens.others

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class OthersViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    val activityName = mutableStateOf("")
    val activityNameSpecify = mutableStateOf("")
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
            0, 0.0, listOf(),0)
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

    fun submitClickHandler(){
        step.value++
    }
}