package com.example.smartfarming.ui.home.composables

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.PestControl
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun TaskCard2(task: Task){
    val activity = LocalContext.current as Activity
    
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .height(215.dp)
            .padding(top = 2.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White
    ) {
        Column(
            Modifier
                .padding(vertical = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                taskIcon2(task.activity_type),
                contentDescription = null,
                modifier = Modifier.size(65.dp),
                tint = taskIconTint(task.activity_type)
            )
            Text(text = task.name, color = Color.Black, style = MaterialTheme.typography.body1)
            Text(text = task.garden_name, color = BorderGray, style = MaterialTheme.typography.subtitle1)
            RemainingDays(task)
        }
    }
    
}

@Composable
fun RemainingDays(task: Task){
    Row(
        Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(text = "10 روز", color = taskIconTint(task.activity_type).copy(alpha = 0.6f), style = MaterialTheme.typography.subtitle2)

        LinearProgressIndicator(
            .70f,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(10.dp)
                .clip(MaterialTheme.shapes.large),
            color = taskIconTint(task.activity_type),
            backgroundColor = taskIconTint(task.activity_type).copy(alpha = 0.3f)
        )
    }
}

fun taskIcon2(activityType: String) : ImageVector {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> Icons.Outlined.Compost
        ActivityTypesEnum.IRRIGATION.name -> Icons.Outlined.WaterDrop
        ActivityTypesEnum.PESTICIDE.name -> Icons.Outlined.PestControl
        else -> Icons.Outlined.Agriculture
    }
}

fun taskIconTint(activityType: String) : Color {
    return when (activityType) {
        ActivityTypesEnum.FERTILIZATION.name -> Purple500
        ActivityTypesEnum.IRRIGATION.name -> BlueIrrigation
        ActivityTypesEnum.PESTICIDE.name -> YellowPesticide
        else -> MainGreen
    }
}


@Preview
@Composable
fun TaskCard2Preview(){
    TaskCard2(task = Task(0,
        "ولک پاشی",
        activity_type = ActivityTypesEnum.FERTILIZATION.name,
        description = "به دلیل عدم تامین نیاز سرمایی",
        start_date = "",
        finish_date = "",
        garden_name = "محمد",
        recommendations = "روغن ولک",
        user_id = 5,
        seen = false)
    )
}