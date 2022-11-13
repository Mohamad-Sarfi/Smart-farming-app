package com.example.smartfarming.ui.tasks_notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.home.composables.TaskCard

@Composable
fun TasksNotificationCompose(){

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "پیشنهادات باغداری",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    color = MainGreen
                )

                Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                )

            }

            // New Tasks
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(2.dp)
                            .background(MainGreen)
                            .clip(RectangleShape)
                            .padding(top = 2.dp)
                    )
                    
                    Text(
                        text = "جدید",
                        style = MaterialTheme.typography.body1,
                        color = MainGreen,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )
                    
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(2.dp)
                            .background(MainGreen)
                            .clip(RectangleShape)
                            .padding(top = 2.dp)
                    )
                }

//                LazyColumn() {
//                    items(taskList) { task ->
//                        TaskCard(task = task)
//                    }
//                }

                
            }

            // Old Tasks
            Column(modifier = Modifier.padding(top = 30.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(2.dp)
                            .background(MainGreen)
                            .clip(RectangleShape)
                            .padding(top = 2.dp)
                    )

                    Text(
                        text = "دیده شده",
                        style = MaterialTheme.typography.body1,
                        color = MainGreen,
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    )

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(2.dp)
                            .background(MainGreen)
                            .clip(RectangleShape)
                            .padding(top = 2.dp)
                    )
                }
            }


        }
    }
}

