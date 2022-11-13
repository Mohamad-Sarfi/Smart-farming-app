package com.example.smartfarming.ui.home.composables

import android.app.Activity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.utils.getTaskColor
import com.example.smartfarming.utils.getTaskIcon

@Composable
fun TaskCard2(task: Task, navController: NavHostController, oneStepClick : Boolean = false, deleteTask: (Task) -> Unit, clickHandler : () -> Unit){

    var clicked by remember {
        mutableStateOf(false)
    }
    val cardHeight by animateDpAsState(
        targetValue = if (clicked) 260.dp else 210.dp,
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
            .clickable {
                if (clicked || oneStepClick) {
                    clickHandler()
                } else {
                    clicked = !clicked
                }
            }
            .padding(top = 2.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(15.dp),
        backgroundColor = Color.White,
        border = if (clicked) BorderStroke(2.dp, getTaskColor(task.activityType)) else BorderStroke(0.dp, Color.White),
    ) {
        Column(
            Modifier
                .padding(vertical = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                getTaskIcon(task.activityType),
                contentDescription = null,
                modifier = Modifier.size(65.dp),
                tint = getTaskColor(task.activityType)
            )
            Text(text = task.name, color = Color.Black, style = MaterialTheme.typography.body1)
            Text(text = "باغ _", color = BorderGray, style = MaterialTheme.typography.overline, textAlign = TextAlign.Justify)
            RemainingDays(task)
            if (clicked){
                ButtonRow(task){
                    deleteTask(it)
                }
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

        Text(text = "10 روز", color = getTaskColor(task.activityType).copy(alpha = 0.6f), style = MaterialTheme.typography.subtitle2)

        LinearProgressIndicator(
            .70f,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(10.dp)
                .clip(MaterialTheme.shapes.large),
            color = getTaskColor(task.activityType),
            backgroundColor = getTaskColor(task.activityType).copy(alpha = 0.3f)
        )
    }
}





@Composable
private fun ButtonRow(task: Task, deleteTask: (Task) -> Unit){

    var openDialog by remember {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .clickable { openDialog = !openDialog }
        )
        Text(text = task.description, style = MaterialTheme.typography.subtitle1)
    }

    if (openDialog){
        DeleteDialog(openDialog,task, deleteTask = {deleteTask(it)}){openDialog = it}
    }

}

@Composable
fun DeleteDialog(openDialog : Boolean,task: Task ,deleteTask : (Task) -> Unit ,setOpenDialog : (Boolean) -> Unit){
    AlertDialog(
        onDismissRequest = { setOpenDialog(!openDialog) },
        title = {
            Text(text = "حذف این پیشنهاد", style = MaterialTheme.typography.body1)
        },
        confirmButton = {
            Button(onClick = {
                deleteTask(task)
                setOpenDialog(!openDialog)
            }) {
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

