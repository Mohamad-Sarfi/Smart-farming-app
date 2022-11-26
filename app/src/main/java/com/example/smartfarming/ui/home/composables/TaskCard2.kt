package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.utils.getDateDifferenceWithToday
import com.example.smartfarming.utils.getTaskColor
import com.example.smartfarming.utils.getTaskIcon

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskCard2(task: Task, navController: NavHostController, oneStepClick : Boolean = false, setTaskStatus : (String) -> Unit, deleteTask: (Task) -> Unit, clickHandler : () -> Unit){

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
        elevation = if (task.status == TaskStatusEnum.IGNORED.name) 0.dp else 3.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = if (task.status == TaskStatusEnum.IGNORED.name) Gray200 else Color.White,
        border = if (clicked) BorderStroke(2.dp, getTaskColor(task.activityType)) else BorderStroke(0.dp, Gray200),
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
                tint = getTaskColor(task.activityType))

            Text(text = task.name, color = Color.Black, style = MaterialTheme.typography.body1)
//            Text(text = " باغ ", color = BorderGray, style = MaterialTheme.typography.overline, textAlign = TextAlign.Justify)
//            task.gardenIds.forEach{ _ ->
//            }

            RemainingDays(task){
                setTaskStatus(it)
            }

            if (clicked){
                ButtonRow(task){
                    deleteTask(it)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RemainingDays(task: Task, setTaskStatus: (String) -> Unit = {}){
    Row(
        Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val date = task.expireDuration.split('/')
        val remained = getDateDifferenceWithToday(mapOf("year" to date[0], "month" to date[1], "day" to date[2]))
        val remainingPortion = calculateRemainingPortion(remained, task)
        Log.i("TAG remain", "task remaining days: $remained / ${task.executionTime.toFloat()} = $remainingPortion")

        setTaskAsIgnored(remainingPortion, task){
            setTaskStatus(it)
        }

        LinearProgressIndicator(
            remainingPortion,
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(10.dp)
                .clip(MaterialTheme.shapes.large),
            color = if (remainingPortion == 0f) MaterialTheme.colors.error.copy(.7f) else getTaskColor(task.activityType),
            backgroundColor = if (remainingPortion == 0f) MaterialTheme.colors.error.copy(.5f) else getTaskColor(task.activityType).copy(.3f),
        )

        Text(text = "$remained روز ", color = if (remainingPortion == 0f) MaterialTheme.colors.error.copy(.7f) else getTaskColor(task.activityType), style = MaterialTheme.typography.subtitle2)
    }
}

private fun setTaskAsIgnored(remainingPortion : Float, task: Task, setTaskStatus: (String) -> Unit) {
    if (remainingPortion == 0f && task.status != TaskStatusEnum.IGNORED.name){
        setTaskStatus(TaskStatusEnum.IGNORED.name)
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
        Text(
            text = task.description,
            style = MaterialTheme.typography.subtitle1,
            lineHeight = 25.sp
        )
    }

    if (openDialog){
        DeleteDialog(openDialog,task, deleteTask = {deleteTask(it)}){openDialog = it}
    }
}

private fun calculateRemainingPortion(remained : Long, task: Task) : Float {
    val result = remained.toFloat() / task.executionTime.toFloat()

    return try {
        if (result == Float.NEGATIVE_INFINITY) 0f else result
    } catch (e : Exception) {
        e.printStackTrace()
        0f
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
        },
    )
}

