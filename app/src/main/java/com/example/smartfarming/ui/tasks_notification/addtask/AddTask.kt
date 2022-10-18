package com.example.smartfarming.ui.tasks_notification.addtask

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.addactivities.ui.theme.LightGreen3
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.common_composables.GardenSpinner

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTask() {
    val viewModel : AddTaskViewModel = hiltViewModel()

    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(0.dp),
        backgroundColor = LightGreen3
    ) {
        AddTaskBody(viewModel)
    }
}

@Composable
private fun AddTaskBody(viewModel: AddTaskViewModel) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 30.dp, vertical = 60.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(25.dp),
        backgroundColor = Color.White
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Outlined.Task, contentDescription = null, modifier = Modifier.size(70.dp), tint = MainGreen)

            Crossfade(targetState = viewModel.step.value) { step ->
                when(step){
                    0 -> Step0(viewModel)
                    1 -> Step1()
                    2 -> Step2()
                    else -> Step2()
                }
            }

            BottomRow(viewModel)
        }
    }
}

@Composable
private fun Step0(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        GardenSpinner(gardensList = viewModel.gardenNameList, currentGarden = viewModel.selectedGarden.value){
            viewModel.selectedGarden.value = it
        }
    }
}

@Composable
private fun Step1() {

}

@Composable
private fun Step2() {

}

@Composable
private fun BottomRow(viewModel: AddTaskViewModel) {

    val nextBtnWidth by animateFloatAsState(if (viewModel.step.value == 0) .9f else .8f)

    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        AnimatedVisibility(visible = viewModel.step.value != 0) {
            Button(
                modifier = Modifier.fillMaxWidth(.3f),
                onClick = { viewModel.decreaseStep() },
                shape = MaterialTheme.shapes.medium,) {
                Text(text = "قبلی", style = MaterialTheme.typography.body1)
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(nextBtnWidth)
                .padding(5.dp),
            onClick = { viewModel.increaseStep() },
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(text = "بعدی", style = MaterialTheme.typography.body1)
        }

    }
}

@Preview
@Composable
fun PreviewAddTask() {
    //AddTaskBody()
}