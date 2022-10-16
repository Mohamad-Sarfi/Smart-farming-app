package com.example.smartfarming.ui.gardenprofile.report

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.PesticideEntity
import com.example.smartfarming.utils.PersianCalender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {

    private val thisYear = PersianCalender.getShamsiDateMap()["year"].toString()
    val irrigationsNumber = mutableStateOf(0)
    val fertilizationNumber = mutableStateOf(0)
    val pesticidesNumber = mutableStateOf(0)
    val selectedYear = mutableStateOf(thisYear)
    val allPests = mutableStateOf<List<PesticideEntity>?>(value = null)

    fun getAllActivitiesCount(gardenName: String){
        Log.i("TAG_all", "clicked")
        viewModelScope.launch {
            getPesticidesNumber(gardenName)
            getIrrigationsNumber(gardenName)
            getFertilizationsNumber(gardenName)
        }
    }

    private suspend fun getIrrigationsNumber(gardenName : String){
        irrigationsNumber.value = repo.getIrrigationByGardenName(gardenName).size
    }

    private suspend fun getFertilizationsNumber(gardenName: String){
        repo.getFertilizationByGardenName(gardenName).collect{
            fertilizationNumber.value = it.size
            Log.i("TAG_pestNUM", "clicked11")
        }
    }

    private suspend fun getPesticidesNumber(gardenName: String){
        Log.i("TAG_pestNUM", "clicked")
        pesticidesNumber.value = repo.getPesticidesByGardenName(gardenName).firstOrNull()?.size ?: 0
    }

    fun getAllPesticides(gardenName: String){
        viewModelScope.launch {
            repo.getPesticidesByGardenName(gardenName).collect{
                allPests.value = it
            }
        }
    }
}

class ReportViewModelFactory(val repo: GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}