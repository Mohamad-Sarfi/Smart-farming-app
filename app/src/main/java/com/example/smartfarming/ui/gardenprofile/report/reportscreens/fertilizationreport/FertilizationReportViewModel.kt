package com.example.smartfarming.ui.gardenprofile.report.reportscreens.fertilizationreport

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.FertilizationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FertilizationReportViewModel @Inject constructor(private val repo : GardenRepo ) : ViewModel(){
    var fertilizationList = mutableStateListOf<FertilizationEntity>()

    fun getFertilizationsForGarden(gardenName : String){
        viewModelScope.launch {
            repo.getFertilizationByGardenName(gardenName).collect{
                fertilizationList.clear()
                for (i in it){
                    fertilizationList.add(i)
                }
            }
        }
    }

    fun deleteFertilization(fertilizationEntity: FertilizationEntity){
        viewModelScope.launch {
            repo.deleteFertilization(fertilizationEntity)
        }
    }
}