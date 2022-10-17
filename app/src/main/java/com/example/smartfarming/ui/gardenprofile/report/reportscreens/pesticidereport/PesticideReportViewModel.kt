package com.example.smartfarming.ui.gardenprofile.report.reportscreens.pesticidereport

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.PesticideEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PesticideReportViewModel @Inject constructor(private val repo : GardenRepo): ViewModel(){
    var pesticideList = mutableStateListOf<PesticideEntity>()

    fun getPesticideForGarden(gardenName : String){
        viewModelScope.launch {
            val pesticideListStatic = repo.getPesticidesByGardenName(gardenName).firstOrNull()
            if (pesticideListStatic != null) {
                updateStateList(pesticideListStatic)
            }
        }
    }

    private fun updateStateList(staticList : List<PesticideEntity>){
        pesticideList.clear()
        for (i in staticList){
            pesticideList.add(i)
        }
    }

    fun deletePesticide(pesticideEntity: PesticideEntity){
        viewModelScope.launch {
            repo.deletePesticide(pesticideEntity)
            pesticideList.remove(pesticideEntity)
        }
    }
}