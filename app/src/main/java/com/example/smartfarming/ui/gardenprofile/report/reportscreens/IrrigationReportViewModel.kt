package com.example.smartfarming.ui.gardenprofile.report.reportscreens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.IrrigationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IrrigationReportViewModel @Inject constructor(val repo : GardenRepo) : ViewModel() {
    var irrigationList = mutableStateListOf<IrrigationEntity>()

    fun getIrrigationForGarden(gardenName : String){
        viewModelScope.launch {
            val irrigationListStatic = repo.getIrrigationByGardenName(gardenName)
            Log.i("TAG_xx", "${irrigationListStatic}")
            for (i in irrigationListStatic){
                irrigationList.add(i)
            }
        }
    }
}