package com.example.smartfarming.ui.addactivity.activityscreens.others

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.OtherActivityEntity
import com.example.smartfarming.utils.initialGarden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OthersViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    val activityName = mutableStateOf("")
    val activityCause = mutableStateOf("")
    val activityDescription = mutableStateOf("")
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
        value = initialGarden
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

    fun submitClickHandler(gardenName : String){
        if (step.value == 1){
            insertOtherActivity2Db(gardenName)
        }
        step.value++
    }

    fun setActivityCause(cause : String){
        activityCause.value = cause
    }

    fun setActivityDescription(description : String){
        activityDescription.value = description
    }

    private fun insertOtherActivity2Db(gardenName: String){
        viewModelScope.launch {
            repo.insertOtherActivity(
                OtherActivityEntity(
                    0,
                    name = activityName.value,
                    date = date.value["year"] +"/" + date.value["month"] + "/" + date.value["day"],
                    description = activityDescription.value,
                    cause = activityCause.value,
                    garden_name = gardenName
                )
            )
            val x = repo.getOtherActivitiesByGardenName(garden.value!!.title)
            Log.i("TAG_zz", "$x")
        }
    }
}