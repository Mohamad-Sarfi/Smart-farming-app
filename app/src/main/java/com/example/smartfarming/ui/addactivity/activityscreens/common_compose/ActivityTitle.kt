package com.example.smartfarming.ui.addactivity.activityscreens.common_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationDark

@Composable
fun ActivityTitle(
    gardenName : String,
    activityName : String,
    icon : ImageVector,
    color: Color
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 30.dp, end = 40.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = "باغ " +  gardenName, style = MaterialTheme.typography.h5, color = color, modifier = Modifier.padding(end = 10.dp))
        Text(text = activityName, style = MaterialTheme.typography.h4, color = color)
        Icon(icon, contentDescription = null, modifier = Modifier.padding(start = 15.dp).size(65.dp), tint = color)
    }
}