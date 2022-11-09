package com.example.smartfarming.ui.addactivities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.smartfarming.R

enum class ScreensEnumActivities(
    val icon : Int,
) {

    HomeActivity(
        icon = R.drawable.home
    ),

    Irrigation(
        icon = 0
    ),

    Fertilization(
        icon = 0
    ),

    Pesticide(
        icon = 0
    ),

    OtherActivities(
        icon = 0
    ),

    ActivityScreen(
        icon = 0
    ),

    IrrigationBody(
        icon = 0
    ),

    FertilizationBody(
        icon =0
    ),
    PesticideBody(
        icon = 0
    ),
    OtherActivityBody(
        icon = 0
    ),
    MapScreen(
        icon = 0
    ),
    AddGardenScreen(
        icon = 0
    )



    ;

    @Composable
    fun content(onScreenChange: (String) -> Unit) {

    }
}

