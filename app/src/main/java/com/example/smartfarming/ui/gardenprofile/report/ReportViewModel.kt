package com.example.smartfarming.ui.gardenprofile.report

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.PesticideEntity
import com.example.smartfarming.utils.PersianCalender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    private val thisYear = PersianCalender.getShamsiDateMap()["year"].toString()
    val irrigationsNumber = mutableStateOf(0)
    val fertilizationNumber = mutableStateOf(0)
    val pesticidesNumber = mutableStateOf(0)
    val selectedYear = mutableStateOf(thisYear)
    val allPests = mutableStateOf<List<PesticideEntity>?>(value = null)
    val otherActivitiesNumber = mutableStateOf(0)
    val workersNumber = mutableStateOf(0)
    private var gardenId = 0

    fun getAllActivitiesCount(gardenName: String){
        Log.i("TAG_all", "clicked")
        viewModelScope.launch {
            getPesticidesNumber(gardenName)
            getOtherActivitiesNumber(gardenName)
            getIrrigationsNumber(gardenName)
            getFertilizationsNumber(gardenName)
        }
    }

    private suspend fun getIrrigationsNumber(gardenName : String){
        irrigationsNumber.value = repo.getIrrigationByGardenName(gardenName).size
    }

    private suspend fun getFertilizationsNumber(gardenName: String){
        gardenId = repo.getGardenByName(gardenName).id

        repo.getFertilizationByGardenId(gardenId).collect{
            fertilizationNumber.value = it.size
            Log.i("TAG_pestNUM", "clicked11")
        }
    }

    private suspend fun getPesticidesNumber(gardenName: String){
        pesticidesNumber.value = repo.getPesticidesByGardenName(gardenName).firstOrNull()?.size ?: 0
    }

    fun getOtherActivitiesNumber(gardenName: String){
        viewModelScope.launch {
            val r = repo.getOtherActivitiesByGardenName(gardenName)
            otherActivitiesNumber.value = r.size
        }
    }

    fun getAllPesticides(gardenName: String){
        viewModelScope.launch {
            repo.getPesticidesByGardenName(gardenName).collect{
                allPests.value = it
            }
        }
    }

}