package com.example.smartfarming.ui.addactivities.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.repositories.activities.ActivitiesRemoteRepo
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.FertilizationEntity
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.enums.FertilizationUsageType
import com.example.smartfarming.data.room.entities.enums.GardenAreaUnitEnum
import com.example.smartfarming.utils.initialGarden
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class FertilizationViewModel @Inject constructor(val repo : GardenRepo, val activitiesRemoteRepo: ActivitiesRemoteRepo) : ViewModel() {

    var step = mutableStateOf(0)
    private val garden = MutableLiveData<Garden>().apply {
        value = initialGarden
    }
    val fertilizationType = mutableStateOf(value = "")
    val fertilizerName = mutableStateListOf<String>()
    val currentFertilizerName = mutableStateOf("")
    val fertilizationDate = mutableStateOf(value = mutableMapOf<String, String>("day" to "", "month" to "", "year" to ""))
    val fertilizationVolume = mutableStateOf<Float>(0f)
    val fertilizationWorker = mutableStateOf(1)
    private var gardenId = 0
    private var fertilizationUsageType = "" //used to store in database
    private var volumeUnit = "" //used to store in database

    private fun setGardenId(garden: Garden){
        gardenId = garden.id
    }

    fun setFertilizationType(type : String){
        fertilizationType.value = type
    }

    fun addFertilizer(name : String){
        if (name != ""){
            fertilizerName.add(name)
        }
    }

    fun removeFertilizer(name : String){
        fertilizerName.remove(name)
    }

    fun setFertilizationVolume(volume : Float){
        fertilizationVolume.value = volume
    }

    fun decideFertilizationVolume(fertilizationType: String, typeList: Array<String>) : String {
        return when(fertilizationType){
            typeList[0] -> "کیلو گرم"
            typeList[1] -> "لیتر در 1000"
            typeList[2] -> "لیتر"
            else -> "کیلو گرم"
        }
    }

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
            fertilizationVolume.value = 0f
        } else {
            try {
                fertilizationVolume.value = value.toFloat()
            } catch (e : Exception) {
                fertilizationVolume.value = 0f
            }
        }
    }

    private fun getGardenByName(gardenName : String) {
        viewModelScope.launch(Dispatchers.Main) {
            garden.value  = repo.getGardenByName(gardenName)
            setGardenId(garden.value!!)
        }
    }

    fun getGarden(gardenName: String) : MutableLiveData<Garden> {
        getGardenByName(gardenName)
        return garden
    }

    fun getGardenById(gardenId: Int) {
        viewModelScope.launch {
            garden.value = repo.getGardenById(gardenId)
        }
    }

    fun increaseStep(context : Context){
        if (fertilizationDate.value["day"] == "" || fertilizationType.value == "" || fertilizerName.isEmpty()){
            Toast.makeText(context, "تمام فیلدها را پر کنید", Toast.LENGTH_SHORT).show()
        } else {
            step.value++
        }

    }
    fun decreaseStep(){
        if (step.value == 1) step.value--
    }


    fun submitClickHandler(auth: String, context : Context){
        if (step.value == 1){
            insertFertilizationt2Db()
            addFertilizationToServer(auth)
        }
        increaseStep(context)
    }

    fun areValuesSet() : Boolean {
        return true
    }

    fun insertFertilizationt2Db(){
        viewModelScope.launch {
            repo.insertFertilization(
                FertilizationEntity(
                    id = 0,
                    name = fertilizationListToString(fertilizerName),
                    fertilization_type = fertilizationType.value,
                    time = "${fertilizationDate.value["year"]}/${fertilizationDate.value["month"]}/${fertilizationDate.value["day"]} 00:00:00",
                    volumePerUnit = fertilizationVolume.value.toInt(),
                    gardenId = gardenId,
                    usageType = fertilizationUsageType,
                    volumeUnit = volumeUnit
                )
            )
        }
    }

    private fun fertilizationListToString(fertilizerList : List<String>) : String {

        var fertilizerString = ""

        for (f in fertilizerList){
            fertilizerString += f
            fertilizerString += ","
        }

        return fertilizerString
    }

    private fun decideUnit(fertilizationType: String, typeList: Array<String>) {
        volumeUnit = when(fertilizationType){
            typeList[0] -> GardenAreaUnitEnum.KILOGRAM.name
            typeList[1] -> GardenAreaUnitEnum.LITER.name
            typeList[2] -> GardenAreaUnitEnum.LITER.name
            else -> GardenAreaUnitEnum.KILOGRAM.name
        }
    }

    fun setFertilizationType(selectedType : String, typeList: Array<String>){
        decideUnit(selectedType, typeList)

        fertilizationUsageType = when(selectedType){
            typeList[0] -> FertilizationUsageType.BURIABLE.name
            typeList[1] -> FertilizationUsageType.SPRAY.name
            typeList[2] -> FertilizationUsageType.SOLUBLE.name
            else -> FertilizationUsageType.BURIABLE.name
        }
    }

    fun addFertilizationToServer(auth : String){

        viewModelScope.launch {
            val response = activitiesRemoteRepo.addFertilization(
                auth = auth,
                fertilizationEntity = FertilizationEntity(
                    id = 0,
                    name = fertilizationListToString(fertilizerName),
                    fertilization_type = fertilizationType.value,
                    time = "${fertilizationDate.value["year"]}/${fertilizationDate.value["month"]}/${fertilizationDate.value["day"]} 00:00:00",
                    volumePerUnit = fertilizationVolume.value.toInt(),
                    gardenId = gardenId,
                    usageType = fertilizationUsageType,
                    volumeUnit = volumeUnit
                )
            )

            checkServerResponse(response)
        }
    }

    private fun checkServerResponse(response : Resource<FertilizationEntity>) {
        when(response){
            is Resource.Success<*> -> Log.i("TAG fertilization response", "${response.value}")
            else -> Log.i("TAG fertilization response", "Wrong $response")
        }
    }
}