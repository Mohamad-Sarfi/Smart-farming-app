package com.example.smartfarming.ui.harvest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Harvest
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class HarvestViewModel(val repo : GardenRepo) : ViewModel() {

    var harvestDate =
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))


    @JvmName("getHarvestDate1")
    fun getHarvestDate() : MutableState<MutableMap<String, String>> {
        return harvestDate
    }

    fun setDate(date : MutableMap<String, String>){
        harvestDate.value = date
    }

    fun getGardens() : LiveData<List<Garden>>{
        var list = liveData<List<Garden>> {  }

        viewModelScope.launch() {
            list = repo.getGardens().asLiveData()
        }
        return list
    }

    var selectedGarden = MutableLiveData<String>("")
    var harvestWeight = MutableLiveData<Float>()
    var harvestType = MutableLiveData<String>()


    fun addHarvest2DB(harvest: Harvest){
        viewModelScope.launch {
            repo.insertHarvest(harvest)
        }
    }

}

class HarvestViewModelFactory(val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HarvestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HarvestViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}