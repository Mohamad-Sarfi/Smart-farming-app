package com.example.smartfarming.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.PestControl
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.Blue500
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple500
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

fun getTaskIcon(activityType: String) : ImageVector {

    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> Icons.Outlined.Compost
        ActivityTypesEnum.IRRIGATION.name -> Icons.Outlined.WaterDrop
        ActivityTypesEnum.PESTICIDE.name -> Icons.Outlined.PestControl
        else -> Icons.Outlined.Agriculture
    }
}

fun getTaskName(activityType: String) : String {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> "تغذیه"
        ActivityTypesEnum.IRRIGATION.name -> "آبیاری"
        ActivityTypesEnum.PESTICIDE.name -> "سم پاشی"
        else -> "سایر"
    }
}

fun getTaskColor(activityType: String) : Color {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> Purple500
        ActivityTypesEnum.IRRIGATION.name -> Blue500
        ActivityTypesEnum.PESTICIDE.name -> YellowPesticide
        else -> MainGreen
    }
}


fun getActivityScreen(activityName: String) : String {
    return when(activityName){
        ActivityTypesEnum.FERTILIZATION.name -> AppScreensEnum.FertilizationScreen.name
        ActivityTypesEnum.IRRIGATION.name -> AppScreensEnum.IrrigationScreen.name
        ActivityTypesEnum.PESTICIDE.name -> AppScreensEnum.PesticideScreen.name
        else -> AppScreensEnum.OtherActivitiesScreen.name
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDateDifferenceWithToday(date : Map<String, String>) : Long {
    val currentDate = PersianCalender.getShamsiDateMap()

//    val date1 = sdf.parse("${currentDate["day"]}/${currentDate["month"]}/${currentDate["year"]}")
//    val date2 = sdf.parse("${date["day"]}/${date["month"]}/${date["year"]}")
//    val date1 = sdf.parse("06/10/2022")
//    val date2 = sdf.parse("01/10/2022")
//    val diffInMillies = date2.time - date1.time
//    val x = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusDays(15))

    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy")
    var day1 = ""
    var day2 = ""
    var month1 = ""
    var month2 = ""

    day1 = if (date["day"]!!.length == 1){
        "0" + date["day"]
    } else {
        date["day"]!!
    }

    month1 = if (date["month"]!!.length == 1){
        "0" + date["month"]
    } else {
        date["month"]!!
    }

    month2 = if (currentDate["month"]!!.toString().length == 1){
        "0" + currentDate["month"]
    } else {
        currentDate["month"].toString()
    }

    day2 = if (currentDate["day"]!!.toString().length == 1){
        "0" + currentDate["day"]
    } else {
        currentDate["day"].toString()
    }

    val inputString1 = "$day2 $month2 ${currentDate["year"]}"
    val inputString2 = "$day1 $month1 ${date["year"]}"
//    val inputString1 = "12 07 1401"
//    val inputString2 = "12 08 1401"

    val x = LocalDate.parse(inputString1, dtf)
    val y = LocalDate.parse(inputString2, dtf)

    return ChronoUnit.DAYS.between(x, y)
}