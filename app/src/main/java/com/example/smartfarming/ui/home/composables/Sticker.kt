package com.example.smartfarming.ui.home.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.adduser.ui.theme.*


@Composable
fun Sticker(task: Task){

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(7.dp)
            .clip(CircleShape)
            .background(backgroundColor(task.activity_type))
            .size(45.dp)
            .clickable {
                       Toast.makeText(context, "${task.name}", Toast.LENGTH_SHORT).show()
            }
            ,
    ){
        Icon(
            iconPainter(activityType = task.activity_type),
            contentDescription = "",
            modifier = Modifier
                .size(27.dp)
                .align(Alignment.Center)
            ,
            tint = Color.White
        )
    }
}

fun backgroundColor(activityType : String) : Color{
    return when(activityType){
        ActivityTypesEnum.IRRIGATION.name -> BlueWatering
        ActivityTypesEnum.FERTILIZATION.name -> RedFertilizer
        ActivityTypesEnum.PESTICIDE.name -> YellowPesticide
        ActivityTypesEnum.PRUNE.name -> PurplePrune
        else -> MainGreen
    }
}

@Composable
fun iconPainter(activityType: String) : Painter {
    return when(activityType){
        ActivityTypesEnum.IRRIGATION.name -> painterResource(id = R.drawable.irrigation_line1)
        ActivityTypesEnum.FERTILIZATION.name -> painterResource(id = R.drawable.fertilizer_line)
        ActivityTypesEnum.PESTICIDE.name -> painterResource(id = R.drawable.pesticide_line)
        ActivityTypesEnum.PRUNE.name -> painterResource(id = R.drawable.pruning_line)
        else -> painterResource(id = R.drawable.shove_line)
    }
}