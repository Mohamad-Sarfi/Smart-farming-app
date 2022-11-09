package com.example.smartfarming.ui.tasks_notification.addtask

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray
import com.example.smartfarming.utils.ACTIVITY_LIST

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ActivitySpinner(selectedActivity : String, setSelectedActivity : (String) -> Unit) {
    val activityList = ACTIVITY_LIST
    var expanded by remember { mutableStateOf(false)}
    val transitionState = remember { MutableTransitionState(expanded).apply { targetState = !expanded } }
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val arrowRotateDegree by transition.animateFloat({ tween(delayMillis = 50) }, label = "rotationDegree") { if (expanded) 180f else 0f }

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.8f),
        elevation = 3.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = LightGray
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
                .padding(vertical = 5.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .size(50.dp)
                    .rotate(arrowRotateDegree))

            Text(
                text = if (selectedActivity.isNullOrEmpty()) "انتخاب فعالیت" else selectedActivity,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            activityList.forEach { activity ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    setSelectedActivity(activity)
                }) {
                    Text(
                        text = activity,
                        modifier = Modifier
                            .width(200.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

















