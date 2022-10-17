package com.example.smartfarming.ui.gardenprofile.report.reportscreens.otheractivitiesreport

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.OtherActivityEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherActivitiesReportViewModel @Inject constructor(private val repo : GardenRepo) : ViewModel() {
    val otherActivitiesList = mutableStateListOf<OtherActivityEntity>()

    fun getOtherActivitiesForGarden(gardenName : String){
        viewModelScope.launch {
            val otherActivitiesListStatic = repo.getOtherActivitiesByGardenName(gardenName)
            updateStateList(otherActivitiesListStatic)
        }
    }

    private fun updateStateList(staticList: List<OtherActivityEntity>){
        otherActivitiesList.clear()

        for (i in staticList){
            otherActivitiesList.add(i)
        }
    }

    fun deleteOtherActivity(otherActivityEntity: OtherActivityEntity){
        viewModelScope.launch {
            repo.deleteOtherActivity(otherActivityEntity)
            otherActivitiesList.remove(otherActivityEntity)
        }
    }
}