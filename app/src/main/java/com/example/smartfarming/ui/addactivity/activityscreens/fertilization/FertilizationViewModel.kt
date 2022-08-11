package com.example.smartfarming.ui.addactivities.viewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.FertilizationEntity
import com.example.smartfarming.data.room.entities.Garden
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class FertilizationViewModel(val repo : GardenRepo) : ViewModel() {

    var step = mutableStateOf(0)



    private val garden = MutableLiveData<Garden>().apply {
        value = Garden(0, "", 0, "", "", "", "", "", 0.0, 0.0,
            0.0, 0)
    }
    val fertilizationType = mutableStateOf(value = "")

    val fertilizerName = mutableStateListOf<String>()

    val currentFertilizerName = mutableStateOf("")

    val fertilizationDate =
        mutableStateOf(value = mutableMapOf<String, String>("day" to "", "month" to "", "year" to ""))

    val fertilizationVolume = mutableStateOf<Float>(0f)

    val fertilizationWorker = mutableStateOf(1)

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

    ///////////////////////////////////////////////// Button click handler
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


    fun submitBtnHandler(context : Context, gardenName: String, navHostController: NavHostController){
        if (step.value == 0){
            increaseStep(context)
        } else {
            if (fertilizationVolume.value == 0f){
                Toast.makeText(context, "تمام فیلد ها را پر کنید", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, fertilizationListToString(fertilizerName), Toast.LENGTH_SHORT).show()
                submitToDb(gardenName)
                navHostController.popBackStack()
            }
        }
    }

    fun submitToDb(gardenName: String){
        viewModelScope.launch {
            repo.insertFertilization(
                FertilizationEntity(
                    id = 0,
                    name = fertilizationListToString(fertilizerName),
                    fertilization_type = fertilizationType.value,
                    date = "${fertilizationDate.value["year"]}/${fertilizationDate.value["month"]}/${fertilizationDate.value["day"]}",
                    volume = fertilizationVolume.value,
                    garden_name = gardenName
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

}



class FertilizationViewModelFactory(private val repo : GardenRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FertilizationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FertilizationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}