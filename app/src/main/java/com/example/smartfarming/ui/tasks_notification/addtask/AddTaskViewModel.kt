package com.example.smartfarming.ui.tasks_notification.addtask

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskPriorityEnum
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import com.example.smartfarming.data.room.entities.enums.TaskTypeEnum
import com.example.smartfarming.utils.ACTIVITY_LIST
import com.example.smartfarming.utils.PersianCalender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val repo : GardenRepo) : ViewModel(){
    var step = mutableStateOf(0)
    val MAX_STEP = 4
    val gardensList = mutableStateOf<List<Garden>>(listOf())
    val gardenNameList = mutableStateListOf<String>()
    val selectedGarden = mutableStateOf("")
    val selectedActivity = mutableStateOf("")
    val showCustomActivity = mutableStateOf(false)
    val customActivity = mutableStateOf("")
    val remainingDays = mutableStateOf(0)
    val description = mutableStateOf("")
    val day = mutableStateOf("")
    val month = mutableStateOf("")
    val year = mutableStateOf("")
    val selectedGardens = mutableStateListOf<String>()
    private val gardensId = mutableListOf<Int>()
    val currentDate: MutableMap<String, Int> = PersianCalender.getShamsiDateMap()
    val gardenNotSet = mutableStateOf(false)
    val activityNotSet = mutableStateOf(false)
    val dateNotSet = mutableStateOf(false)
    val showToast = mutableStateOf(false)
    var toastText = ""

    init {
        getGardens()
        setCurrentDate()
    }

    private fun setCurrentDate(){

        day.value = currentDate["day"].toString()
        month.value = currentDate["month"].toString()
        year.value = currentDate["year"].toString()
    }

    fun setRemainingDays(){
        /**
         * Calculates the difference between the current day and the selected date
         */
        if (month.value.toInt() == currentDate["month"]!!){
            remainingDays.value = day.value.toInt() - currentDate["day"]!!
        } else if (month.value.toInt() > currentDate["month"]!!){
            if ((month.value.toInt() - currentDate["month"]!!) > 1){
                if (currentDate["month"]!! < 7){
                    remainingDays.value = (month.value.toInt() - currentDate["month"]!!) * 31 + day.value.toInt()
                } else {
                    remainingDays.value = (month.value.toInt() - currentDate["month"]!!) * 30 + day.value.toInt()
                }
            } else {
                if (currentDate["month"]!! < 7){
                    remainingDays.value = (31 - currentDate["day"]!!) + day.value.toInt()
                } else {
                    remainingDays.value = (30 - currentDate["day"]!!) + day.value.toInt()
                }
            }
        }
    }

    private fun getGardens(){
        viewModelScope.launch {
            repo.getGardens().collect{
                gardensList.value = it
                populateGardensName()
            }
        }
    }

    private fun populateGardensName(){
        for (g in gardensList.value){
            gardenNameList.add(g.title)
        }
    }

    fun setDescription(txt : String){
        description.value = txt
    }

    fun setRemainingDays(num : Int){
        remainingDays.value = num
        calculateFinishDate()
    }

    fun setCustomActivity(txt : String){
        customActivity.value = txt
    }

    fun increaseStep(){
        if (step.value <= MAX_STEP) {
            step.value++
            Log.i("TAG task step", "${step.value}")
        }
    }

    fun decreaseStep(){
        if (step.value > 0) step.value--
    }

    fun setSelectedGarden(garden: String){
        selectedGarden.value = garden
    }

    fun handleActivitySelection(activity: String){
        setSelectedActivity(activity)
        if (isOtherActivity()){
            showCustomActivity.value = true
        } else {
            increaseStep()
        }
    }

    private fun setSelectedActivity(activity : String){
        selectedActivity.value = activity
    }

    private fun isOtherActivity() : Boolean{
        return selectedActivity.value == "سایر"
    }

    private fun calculateFinishDate(){
        val todayDate = PersianCalender.getCurrentDatePlusDays(remainingDays.value)
        day.value = todayDate["day"].toString()
        month.value = todayDate["month"].toString()
        year.value = todayDate["year"].toString()
    }

    fun submitClickHandler(){
        if (step.value == 0) {
            // garden
            if (selectedGardens.isEmpty()){
                toastText = "نام باغ را وارد کنید"
                showToast.value = true
                showToast.value = false
            } else {
                increaseStep()
            }
        } else if (step.value == 1){
            // activity type
            if (selectedActivity.value == ""){
                toastText = "نوع فعالیت را مشخص کنید"
                showToast.value = true
                showToast.value = false
            } else {
                increaseStep()
            }
        } else if (step.value == 2) {
            //date
            increaseStep()
        }
        else if (step.value == MAX_STEP - 1){
            insertTask2Db()
            increaseStep()
        }
    }

    private fun insertTask2Db(){
        viewModelScope.launch {
            repo.insertTask(
                Task(
                    activityType = getActivityType(),
                    description = description.value,
                    executionTime = remainingDays.value,
                    expireDuration = getFinishDate(),
                    gardenIds = gardensId,
                    id = 0,
                    name = selectedActivity.value,
                    notifyFarmer = false,
                    priority = TaskPriorityEnum.NORMAL.name,
                    status = TaskStatusEnum.TODO.name,
                    taskType = TaskTypeEnum.FARMER.name,
                    userId = 0
                )
            )
        }
    }

    private fun getActivityName() : String{
        if (showCustomActivity.value){
            return customActivity.value
        }
        return selectedActivity.value
    }

    private fun getStartDate() : String{
        val currentDate = PersianCalender.getShamsiDateMap()
        return "${currentDate["year"]}/${currentDate["month"]}/${currentDate["day"]}"
    }

    private fun getFinishDate() : String {
        return "${year.value}/${month.value}/${day.value}"
    }

    private fun getActivityType() : String {
        return when(selectedActivity.value) {
            ACTIVITY_LIST[0] -> ActivityTypesEnum.IRRIGATION.name
            ACTIVITY_LIST[1]  -> ActivityTypesEnum.PESTICIDE.name
            ACTIVITY_LIST[2]  -> ActivityTypesEnum.FERTILIZATION.name
            ACTIVITY_LIST[3]  -> ActivityTypesEnum.Other.name
            else -> ActivityTypesEnum.Other.name
        }
    }

    fun addGarden(gardenName : String) {
        if (gardenName !in selectedGardens){
            selectedGardens.add(gardenName)
            gardensId.add(getGardenIdByName(gardenName))
        }

    }

    private fun getGardenIdByName(gardenName : String) : Int{
        for (garden in gardensList.value){
            if (garden.title == gardenName){
                return garden.id
            }
        }
        return 0
    }

    fun removeGarden(gardenName: String) {
        selectedGardens.remove(gardenName)
    }
}