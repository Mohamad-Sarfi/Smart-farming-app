package com.example.smartfarming.ui.home.composables

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addgarden.AddGarden

@Composable
fun TasksColumn(taskList : List<Task>, gardenList : List<Garden>?){
    
    val context = LocalContext.current
    
    if (gardenList.isNullOrEmpty()){
        // When there are no gardens added yet, this will be displayed
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, AddGarden::class.java)
                    context.startActivity(intent)
                }
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sprout),
                contentDescription = "",
                modifier = Modifier
                    .padding(10.dp)
                    .size(110.dp)
            )

            Text(
                text = "اولین باغ خود را اضافه کنید",
                style = MaterialTheme.typography.body2,
                color = MainGreen
            )
        }
    }
    else {
        // In case gardens are added
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "باغداری شما",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 10.dp),
                color = MainGreen
            )
            Icon(
                Icons.Default.ArrowDownward,
                contentDescription = "",
                tint = MainGreen
            )
        }
        if (taskList.isEmpty()){
            NoTasks()
        } else {
            LazyColumn(){
                items(taskList){
                    TaskCard(task = it)
                }
            }
        }
    }
}

@Composable
fun NoTasks(){
    Column(modifier = Modifier
        .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tasks_done),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
        )
        Text(
            text = "فعالیتی وجود ندارد!",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewNoTasks(){
    NoTasks()
}