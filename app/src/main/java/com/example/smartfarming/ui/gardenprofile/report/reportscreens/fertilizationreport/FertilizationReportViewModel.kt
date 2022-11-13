package com.example.smartfarming.ui.gardenprofile.report.reportscreens.fertilizationreport

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.FertilizationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FertilizationReportViewModel @Inject constructor(private val repo : GardenRepo ) : ViewModel(){
    var fertilizationList = mutableStateListOf<FertilizationEntity>()
    var gardenId = mutableStateOf<Int?>(null)

    fun getFertilizationForGarden(gardenName : String){
        viewModelScope.launch {
            gardenId.value = repo.getGardenByName(gardenName).id

            if (gardenId.value != null){
                repo.getFertilizationByGardenId(gardenId.value!!).collect{
                    fertilizationList.clear()
                    for (i in it){
                        fertilizationList.add(i)
                    }
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