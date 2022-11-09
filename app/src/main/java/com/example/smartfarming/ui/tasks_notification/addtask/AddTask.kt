package com.example.smartfarming.ui.tasks_notification.addtask

import android.annotation.SuppressLint
import android.app.Activity
import android.icu.text.CaseMap.Title
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.addactivities.ui.theme.LightGreen3
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.SuccessCompose
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.GardenSpinner
import com.example.smartfarming.ui.common_composables.HorizontalStepCircle
import com.example.smartfarming.ui.common_composables.PersianDatePicker
import com.example.smartfarming.utils.ACTIVITY_LIST

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTask(navController: NavHostController) {
    val viewModel : AddTaskViewModel = hiltViewModel()

    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(0.dp),
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            Image(painterResource(id = R.drawable.task_back), contentDescription = null, modifier = Modifier
                .fillMaxSize()
                .blur(7.dp),
                contentScale = ContentScale.FillHeight
            )
            
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MainGreen.copy(.2f)))
        }

        AddTaskBody(viewModel, navController)
    }
}

@Composable
private fun AddTaskBody(viewModel: AddTaskViewModel, navController: NavHostController) {
    val activity = LocalContext.current as Activity

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.7f)
            .padding(horizontal = 35.dp, vertical = 0.dp),
            elevation = 3.dp,
            shape = RoundedCornerShape(25.dp),
            backgroundColor = Color.White,
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
                                2 -> Step2(viewModel)
                                3 -> Step3(viewModel)
                                4 -> SuccessCompose(navController){
                                    activity.finish()
                                }
                                else -> Step2(viewModel)
                            }
                        }
                    }

                    BottomRow(viewModel)
                }
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
        Icon(Icons.Default.AlarmAdd, contentDescription = null, modifier = Modifier
            .padding(end = 10.dp)
            .size(35.dp), tint = MaterialTheme.colors.onPrimary)
        Text(text = "ثبت یادآور", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onPrimary)
    }
}

@Composable
private fun Step0(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(Icons.Outlined.Eco, contentDescription = null, modifier = Modifier
            .padding(bottom = 20.dp)
            .size(60.dp), tint = MaterialTheme.colors.primary)

        GardenSpinner(gardensList = viewModel.gardenNameList, currentGarden = viewModel.selectedGarden.value){
            viewModel.setSelectedGarden(it)
            //viewModel.increaseStep()
            viewModel.addGarden(it)
        }

        Row(Modifier.fillMaxWidth().padding(8.dp)) {
            viewModel.selectedGardens.forEach { gardenName ->
                GardenBadge(gardenName){
                    viewModel.removeGarden(it)
                }
            }
        }
    }
}

@Composable
private fun GardenBadge(gardenName : String, deleteItem : (String) -> Unit) {
    Surface(
        modifier = Modifier.padding(4.dp).clickable { deleteItem(gardenName) },
        color = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.small
    ) {
        Text(text = gardenName, color = MaterialTheme.colors.onPrimary, modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp))
    }
}

@Composable
private fun Step1(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(painterResource(id = R.drawable.shovel),
            contentDescription = null, modifier = Modifier
                .padding(bottom = 20.dp)
                .size(60.dp), tint = MaterialTheme.colors.primary)
        ActivitySpinner(selectedActivity = viewModel.selectedActivity.value) {
            viewModel.handleActivitySelection(it)
        }

        if (viewModel.showCustomActivity.value) {
            CustomActivityET(viewModel)
        }
    }
}

@Composable
private fun Step2(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Outlined.Timer,
            contentDescription = null, modifier = Modifier
                .padding(bottom = 20.dp)
                .size(60.dp), tint = MaterialTheme.colors.primary)

        TimeSeekBar(viewModel)
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
private fun Step3(viewModel: AddTaskViewModel) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Icon(Icons.Outlined.Description,
            contentDescription = null, modifier = Modifier
                .padding(bottom = 20.dp)
                .size(60.dp), tint = MaterialTheme.colors.primary)
        DescriptionET(viewModel)
    }
}

@Composable
private fun DescriptionET(viewModel: AddTaskViewModel) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(horizontal = 15.dp),
        elevation = 2.dp,
        backgroundColor = LightGray
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "توضیحات", style = MaterialTheme.typography.subtitle1)
            TextField(
                value = viewModel.description.value,
                onValueChange = {viewModel.setDescription(it)},
                modifier= Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(1f),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.None
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black.copy(.7f),
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    focusedLabelColor = MaterialTheme.colors.primary,
                    unfocusedLabelColor = MaterialTheme.colors.primary
                ),
                textStyle = TextStyle(fontSize = 14.sp, fontFamily = sina, textDirection = TextDirection.Rtl)
            )
        }
    }
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
                    shape = MaterialTheme.shapes.small,) {
                    Text(text = "قبلی", style = MaterialTheme.typography.body1)
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(nextBtnWidth)
                    .padding(5.dp),
                onClick = { viewModel.submitClickHandler() },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = if (viewModel.step.value > viewModel.MAX_STEP - 2) "ثبت" else "بعدی", style = MaterialTheme.typography.body1)
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

        HorizontalStepCircle(step = viewModel.step.value, numberTag = 3, color = MaterialTheme.colors.primary)
    }
}

@Composable
fun TimeSeekBar(viewModel: AddTaskViewModel) {
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(horizontal = 15.dp),
        elevation = 2.dp,
        backgroundColor = LightGray
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Text(text = " روز", style = MaterialTheme.typography.body2, )
                Text(text = viewModel.remainingDays.value.toString(), style = MaterialTheme.typography.body1, )
                Text(text = "مهلت انجام: ", style = MaterialTheme.typography.subtitle1)
            }

            Slider(
                value = viewModel.remainingDays.value.toFloat(),
                onValueChange = {viewModel.setRemainingDays(it.toInt())},
                steps = 15,
                valueRange = 0f..15f,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Text(
                text = "${viewModel.day.value} / ${viewModel.month.value} / ${viewModel.year.value}",
                modifier = Modifier.clickable {
                    showTimePicker = !showTimePicker
                }
            )
        }
    }

    if (showTimePicker) {
        com.example.persian_date_picker.PersianDatePicker(
            onDismiss = {showTimePicker = false}){ date ->
            viewModel.day.value = date["day"]!!
            viewModel.month.value = date["month"]!!
            viewModel.year.value = date["year"]!!
        }
    }
}
