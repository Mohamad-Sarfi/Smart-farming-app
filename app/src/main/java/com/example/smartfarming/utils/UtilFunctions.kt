package com.example.smartfarming.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.PestControl
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple500
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

fun getTaskIcon(activityType: String) : ImageVector {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> Icons.Outlined.Compost
        ActivityTypesEnum.IRRIGATION.name -> Icons.Outlined.WaterDrop
        ActivityTypesEnum.PESTICIDE.name -> Icons.Outlined.PestControl
        else -> Icons.Outlined.Agriculture
    }
}

fun getTaskColor(activityType: String) : Color {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> Purple500
        ActivityTypesEnum.IRRIGATION.name -> BlueIrrigation
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