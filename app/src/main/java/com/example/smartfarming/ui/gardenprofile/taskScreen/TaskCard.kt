package com.example.smartfarming.ui.gardenprofile.taskScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.utils.getTaskColor
import com.example.smartfarming.utils.getTaskIcon

@Composable
fun TaskCard(task: Task) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(.3f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(getTaskIcon(task.activityType), contentDescription = null, tint = getTaskColor(task.activityType))
            Text(text = task.name, color = Color.Black, style = MaterialTheme.typography.body1)

        }
    }
}