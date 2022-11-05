package com.example.smartfarming.ui.home.composables

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen100
import com.example.smartfarming.ui.addactivities.ui.theme.LightGreen1
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.gardenprofile.GardenProfileActivity

@Composable
fun GardenCardHome(
    garden : Garden,
    tasks : List<Task>
){



    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                shape = RoundedCornerShape(25.dp)
                clip = true
            }
            .clickable {
                val intent = Intent(context, GardenProfileActivity::class.java)
                intent.putExtra("gardenName", garden.title)
                context.startActivity(intent)
            }
            .background(MainGreen100),
        verticalAlignment = Alignment.CenterVertically
    ){
        GardenIcon()
        GardenSection(garden.title, tasks)
    }
}

@Composable
fun GardenIcon(){
    Column(
        modifier = Modifier
            .width(120.dp)
            .fillMaxHeight()
            .background(LightGreen1),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(painter = painterResource(id = R.drawable.sprout_white),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight()
                .padding(30.dp)
                ,
            tint = Color.White
        )

    }
}

@Composable
fun GardenSection(
    gardenName : String,
    tasks : List<Task>
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = gardenName, style = MaterialTheme.typography.h5, color = MainGreen)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment =Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val thisGardenTasks = ArrayList<Task>()
            for (task in tasks){
                if (task.garden_name == gardenName){
                    thisGardenTasks.add(task)
                }
            }
            LazyRow{
                items(thisGardenTasks){ task ->
                    Sticker(task)
                }
            }
        }
    }
}
