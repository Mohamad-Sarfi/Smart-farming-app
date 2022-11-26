package com.example.smartfarming.ui.addactivity.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.IrrigationEntity
import com.example.smartfarming.data.room.entities.enums.BudgetCurrencyEnum
import com.example.smartfarming.data.room.entities.enums.GardenAreaUnitEnum
import com.example.smartfarming.data.room.entities.enums.SoilTypeEnum
import com.example.smartfarming.data.room.entities.garden.CoordinateDto
import com.example.smartfarming.data.room.entities.garden.GardenAddress
import com.example.smartfarming.utils.initialGarden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class IrrigationViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    // Step management *********************************************************
    var step = mutableStateOf(0)
    var finished = mutableStateOf(false)

    private val garden = mutableStateOf(initialGarden)

    var dateNotSet = mutableStateOf(false)

    // Irrigation specs
    var irrigationDate =
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))

    var irrigationType =
        mutableStateOf("")
        set(value) { field = value }

    val irrigationDuration =
        mutableStateOf(garden.value.irrigationDuration)

    var waterVolume =
        mutableStateOf(garden.value.irrigationVolume)

    var irrigationWorkers =
        mutableStateOf(1)

    private fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
            waterVolume.value = garden.value.irrigationVolume ?: 2.0
            irrigationDuration.value = garden.value.irrigationDuration
            irrigationType.value = ""
        }
    }

    fun getGarden(gardenName : String) : MutableState<Garden> {
        getGardenByName(gardenName)
        return garden
    }

    fun submitClickHandler(taskId : Int){
        step.value++
        insertIrrigationDB()
    }

    private fun handleTask(taskId: Int){
        /**
         * If the activity is done through a task,
         * here the task status will be updated.
         * If no task is involved, (task id = -1)
         * No task alteration happens
         */
        if (taskId != -1){
            viewModelScope.launch {
                repo.updateTask(taskId)
            }
        }
    }

    fun increaseStep(){
        if (isDateSet()){
            if (step.value == 0) step.value++
        } else {
            dateNotSet.value = true
        }
    }

    fun decreaseStep(){
        if (step.value == 1) step.value--
    }

    fun isDateSet() : Boolean {
        if (irrigationDate.value["day"] == ""){
            return false
        }
        return true
    }

    fun setIrrigationDate(date : MutableMap<String, String>) {
        irrigationDate.value = date
        dateNotSet.value = false
    }

    fun insertIrrigationDB(){
        viewModelScope.launch {
            repo.insertIrrigation(
                IrrigationEntity(
                    0,
                    date = irrigationDate.value["year"]!! + "/" + irrigationDate.value["month"]!! + "/" + irrigationDate.value["day"]!!,
                    irrigation_duration = irrigationDuration.value,
                    irrigation_type = irrigationType.value,
                    irrigation_volume = waterVolume.value,
                    garden_name = garden.value!!.title
                )
            )
        }
    }
}

class IrrigationViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IrrigationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IrrigationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}