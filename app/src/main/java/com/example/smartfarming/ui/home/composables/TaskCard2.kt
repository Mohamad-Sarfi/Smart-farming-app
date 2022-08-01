package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.PestControl
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivity.AddActivityActivity
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.gardenprofile.GardenProfileActivity
import com.example.smartfarming.utils.getTaskColor
import com.example.smartfarming.utils.getTaskIcon

@Composable
fun TaskCard2(task: Task, navController: NavHostController, clickHandler : () -> Unit){

    val activity = LocalContext.current as Activity

    var clicked by remember {
        mutableStateOf(false)
    }
    val cardHeight by animateDpAsState(
        targetValue = if (clicked) 250.dp else 210.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .height(cardHeight)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                if (!clicked){
                    clicked = !clicked
                } else {
                    clickHandler()
                }
            }
            .padding(top = 2.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
        border = if (clicked) BorderStroke(2.dp, getTaskColor(task.activity_type)) else BorderStroke(0.dp, Color.White)
    ) {
        Column(
            Modifier
                .padding(vertical = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                getTaskIcon(task.activity_type),
                contentDescription = null,
                modifier = Modifier.size(65.dp),
                tint = getTaskColor(task.activity_type)
            )
            Text(text = task.name, color = Color.Black, style = MaterialTheme.typography.body1)
            Text(text = task.garden_name, color = BorderGray, style = MaterialTheme.typography.subtitle1)
            RemainingDays(task)
            if (clicked){
                ButtonRow(task.activity_type)
            }
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

        Text(text = "10 روز", color = getTaskColor(task.activity_type).copy(alpha = 0.6f), style = MaterialTheme.typography.subtitle2)

        LinearProgressIndicator(
            .70f,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(10.dp)
                .clip(MaterialTheme.shapes.large),
            color = getTaskColor(task.activity_type),
            backgroundColor = getTaskColor(task.activity_type).copy(alpha = 0.3f)
        )
    }
}





@Composable
fun ButtonRow(activityName : String){

    var openDialog by remember {
        mutableStateOf(false)
    }

    Row(
        Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .clickable { openDialog = !openDialog }
        )
    }

    if (openDialog){
        DeleteDialog(openDialog){openDialog = it}
    }

}

@Composable
fun DeleteDialog(openDialog : Boolean, setOpenDialog : (Boolean) -> Unit){
    AlertDialog(
        onDismissRequest = { setOpenDialog(!openDialog) },
        title = {
            Text(text = "حذف این پیشنهاد", style = MaterialTheme.typography.body1)
        },
        confirmButton = {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "حذف", style = MaterialTheme.typography.subtitle1)
            }
        },
        dismissButton = {
            Button(
                onClick = { setOpenDialog(!openDialog) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = MainGreen)
            ) {
                Text(text = "لغو", style = MaterialTheme.typography.subtitle1)
            }
        },
        text = {
            Text(text = "آیا میخواهید این پیشنهاد را حذف کنید؟", style = MaterialTheme.typography.subtitle2)
        }
    )
}

