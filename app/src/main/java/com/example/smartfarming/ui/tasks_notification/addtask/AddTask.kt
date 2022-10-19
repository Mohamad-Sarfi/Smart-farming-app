package com.example.smartfarming.ui.tasks_notification.addtask

import android.annotation.SuppressLint
import android.app.Activity
import android.icu.text.CaseMap.Title
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.addactivities.ui.theme.LightGreen3
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.SuccessCompose
import com.example.smartfarming.ui.common_composables.GardenSpinner
import com.example.smartfarming.ui.common_composables.HorizontalStepCircle
import com.example.smartfarming.utils.ACTIVITY_LIST

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTask(navController: NavHostController) {
    val viewModel : AddTaskViewModel = hiltViewModel()

    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(0.dp),
        backgroundColor = LightGreen3
    ) {
        AddTaskBody(viewModel, navController)
    }
}

@Composable
private fun AddTaskBody(viewModel: AddTaskViewModel, navController: NavHostController) {
    val activity = LocalContext.current as Activity

    Card(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 35.dp, vertical = 70.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(25.dp),
        backgroundColor = Color.White
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(viewModel)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.7f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Crossfade(targetState = viewModel.step.value, animationSpec = tween(700, 0)) { step ->
                        when(step){
                            0 -> Step0(viewModel)
                            1 -> Step1(viewModel)
                            2 -> Step2()
                            3 -> SuccessCompose(navController){
                                activity.finish()
                            }
                            else -> Step2()
                        }
                    }
                }
                BottomRow(viewModel)
            }
        }
    }
}

@Composable
private fun Title(viewModel: AddTaskViewModel) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(decideIcon(viewModel.selectedActivity.value), contentDescription = null, modifier = Modifier.padding(end = 10.dp).size(45.dp), tint = MaterialTheme.colors.onPrimary)
        Text(text = "ثبت یادآور", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onPrimary)
    }
}

private fun decideIcon(activity: String) : ImageVector{
    return when(activity){
        ACTIVITY_LIST[0] -> Icons.Outlined.WaterDrop
        ACTIVITY_LIST[1] -> Icons.Outlined.BugReport
        ACTIVITY_LIST[2] -> Icons.Outlined.Compost
        ACTIVITY_LIST[3] -> Icons.Outlined.Agriculture
        else -> Icons.Outlined.Eco
    }
}

@Composable
private fun Step0(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        GardenSpinner(gardensList = viewModel.gardenNameList, currentGarden = viewModel.selectedGarden.value){
            viewModel.setSelectedGarden(it)
        }
        ActivitySpinner(selectedActivity = viewModel.selectedActivity.value){
            viewModel.handleActivitySelection(it)
        }

        if (viewModel.showCustomActivity.value){
            CustomActivityET(viewModel)
        }
    }
}

@Composable
private fun CustomActivityET(viewModel: AddTaskViewModel) {
    OutlinedTextField(
        value = viewModel.customActivity.value,
        onValueChange = {viewModel.setCustomActivity(it)},
        modifier= Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.8f),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {viewModel.increaseStep()}
        ),
        label = { Text(text = "نام فعالیت", style = MaterialTheme.typography.subtitle1)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.primary
        )
    )
}

@Composable
private fun Step1(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        TimeSeekBar(viewModel)
        DescriptionET(viewModel)
    }
}

@Composable
private fun Step2() {

}

@Composable
private fun DescriptionET(viewModel: AddTaskViewModel) {
    OutlinedTextField(
        value = viewModel.description.value,
        onValueChange = {viewModel.setDescription(it)},
        modifier= Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(1f),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.None
        ),
        label = { Text(text = "توضیحات", style = MaterialTheme.typography.subtitle1)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.primary
        )
    )
}

@Composable
private fun BottomRow(viewModel: AddTaskViewModel) {

    val nextBtnWidth by animateFloatAsState(if (viewModel.step.value == 0) .9f else .8f)

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        ProgressDots(viewModel)
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
                onClick = { viewModel.submitClickHandler() },
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = if (viewModel.step.value == viewModel.MAX_STEP - 2) "ثبت" else "بعدی", style = MaterialTheme.typography.body1)
            }

        }
    }
}

@Composable
private fun ProgressDots(viewModel: AddTaskViewModel) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        HorizontalStepCircle(step = viewModel.step.value, numberTag = 0, color = MaterialTheme.colors.primary)
        HorizontalStepCircle(step = viewModel.step.value, numberTag = 1, color = MaterialTheme.colors.primary)
        HorizontalStepCircle(step = viewModel.step.value, numberTag = 2, color = MaterialTheme.colors.primary)
    }
}

@Composable
fun TimeSeekBar(viewModel: AddTaskViewModel) {

    Card(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Text(text = " روز", style = MaterialTheme.typography.body1, )
                Text(text = viewModel.remainingDays.value.toString(), style = MaterialTheme.typography.body1, )
                Text(text = "مهلت انجام: ", style = MaterialTheme.typography.body2, )
            }
            Slider(
                value = viewModel.remainingDays.value.toFloat(),
                onValueChange = {viewModel.setRemainingDays(it.toInt())},
                steps = 15,
                valueRange = 0f..15f
            )
            Text(text = "${viewModel.year.value}/${viewModel.month.value}/${viewModel.day.value}")
        }
    }
}