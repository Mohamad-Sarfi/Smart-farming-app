package com.example.smartfarming.ui.addactivity.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.FertilizationEntity
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.PesticideEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class PesticideViewModel(val repo : GardenRepo) : ViewModel() {

    var step = mutableStateOf(0)
    val pesticideWorker = mutableStateOf(1)
    val pesticideVolume = mutableStateOf(0f)
    private var pesticideDate =
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))
    private val pesticideName =
        mutableStateOf("")

    private val garden = MutableLiveData<Garden>().apply {
        value = Garden(0, "", 0, "", "", "", "", "", 0.0, 0.0,
            0, 0.0, listOf(),0) }

    fun updateVolumeValueText(value : String) : String{
        if (value == ""){
            return ""
        }

        try {
            val fValue = value.toFloat()

            if (fValue - fValue.toInt() == 0f){
                return fValue.toInt().toString()
            }
            return fValue.toString()

        } catch (e : Exception){
            return ""
        }
    }

    fun setVolumeValue(value : String){
        if (value == ""){
            pesticideVolume.value = 0f
        } else {
            try {
                pesticideVolume.value = value.toFloat()
            } catch (e : Exception) {
                pesticideVolume.value = 0f
            }
        }
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

    fun setPesticideDate(
        date : MutableMap<String, String>
    ){
        pesticideDate.value = date
    }

    fun getPesticideDate() : MutableState<MutableMap<String, String>>{
        return pesticideDate
    }


    fun setPesticideName(name : String){
        pesticideName.value = name
    }

    fun getPesticideName() : MutableState<String>{
        return pesticideName
    }

    private val pesticidesList = mutableStateOf(mutableListOf<String>())

    fun addPesticide(pesticide : String){
        pesticidesList.value.add(pesticide)
    }

    fun getPesticideList() = pesticidesList.value

    fun removeFromPesticideList(pesticide: String){
        pesticidesList.value.remove(pesticide)
    }

    fun decreaseStep(){
        if (step.value == 1) step.value--
    }

    fun submitClickHandler(){
        if (step.value == 1){
            insertPesticide2Db()
            Log.i("TAG_clicked", "clicked pest")
        }
        step.value++
    }

    private fun insertPesticide2Db(){
        viewModelScope.launch {
            repo.insertPesticide(
                PesticideEntity(
                    0,
                    pesticidesList.value.joinToString(","),
                    garden.value!!.name,
                    pest = "",
                    date = pesticideDate.value["year"] + pesticideDate.value["month"] + pesticideDate.value["day"],
                    pesticideVolume.value
                )
            )
        }
    }
}

class PesticideViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PesticideViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PesticideViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}