package com.example.smartfarming.ui.addactivity.activityscreens.irrigation

import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shower
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationDark
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationLight
import com.example.smartfarming.ui.addactivities.ui.theme.LightBlue
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.ActivityTitle
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.DateSelector
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.WorkerNumber
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.ActivitiesStepBars


@Composable
fun Irrigation(gardenName : String = "محمد"){

    val activity = LocalContext.current as Activity

    val viewmodel : IrrigationViewModel = viewModel(factory = IrrigationViewModelFactory((activity.application as FarmApplication).repo))

    var step = viewmodel.step

    Scaffold(
        Modifier
            .background(LightBlue)
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightBlue)) {
            ActivityTitle(gardenName = gardenName, activityName = "آبیاری", icon = painterResource(id = R.drawable.irrigation_line1), BlueIrrigationDark)
            ActivitiesStepBars(viewmodel.step.value, BlueIrrigationDark, BlueIrrigationLight)
            IrrigationBody(viewmodel)
        }
    }
}

@Composable
fun IrrigationBody(viewModel: IrrigationViewModel){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            ,
        shape = RoundedCornerShape(20.dp),
        elevation = 3.dp
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (bottomRow, body) = createRefs()

            Column(
                Modifier
                    .fillMaxSize()
                    .constrainAs(body) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomRow.top)
                    }
            ) {
                IrrigationBody1(viewModel = viewModel)
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomRow) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(8.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.decreaseStep() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlueIrrigation,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .height(55.dp)
                        .fillMaxWidth(0.2f)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null )
                }

                Button(
                    onClick = {viewModel.increaseStep()},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlueIrrigation,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(0.8f)
                        .height(55.dp)
                ) {
                    Text(text = if (viewModel.step.value == 0 ) "بعدی" else "ثبت اطلاعات", style = MaterialTheme.typography.body2)
                }



            }
        }
    }
}

@Composable
fun IrrigationBody1(viewModel: IrrigationViewModel){
    Crossfade(
        viewModel.step.value,
        animationSpec = tween(1000)
    ) { stepPage ->
        when (stepPage){
            0 ->
                Column(
                Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DateSelector(
                        "آبیاری",
                        date = viewModel.irrigationDate.value,
                        BlueIrrigationDark,
                        LightBlue){
                        viewModel.irrigationDate.value = it
                    }
                    IrrigationVolume(viewModel)
                    IrrigationTime(viewModel)
                }
            1 ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    IrrigationType(viewModel)

                    WorkerNumber(
                        viewModel.step.value,
                        BlueIrrigationDark,
                        LightBlue
                    ){
                        viewModel.irrigationWorkers.value = it
                    }
                }
        }

    }


}



@Composable
fun IrrigationVolume(viewModel: IrrigationViewModel){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "حجم آب", style = MaterialTheme.typography.subtitle1, color = BlueIrrigationDark)
            Icon(Icons.Outlined.WaterDrop, contentDescription = null, tint = BlueIrrigationDark, modifier = Modifier.padding(start = 3.dp))
        }

        OutlinedTextField(
            value = viewModel.waterVolume.value.toString() + " لیتر" ,
            onValueChange = {
                viewModel.waterVolume.value = it.toDouble()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 0.dp)
                .background(LightBlue, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
               backgroundColor = Color.Green,
               textColor = BlueIrrigationDark,
               focusedBorderColor = LightBlue,
               trailingIconColor = BlueIrrigationDark,
               leadingIconColor = BlueIrrigationDark,
                unfocusedBorderColor = LightBlue,


           ),
            leadingIcon = {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.waterVolume.value -= 0.5 }
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.waterVolume.value += 0.5 }
                )
            },
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = sina,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textDirection = TextDirection.ContentOrRtl,
                textAlign = TextAlign.Center
            ),
        )

    }
}


@Composable
fun IrrigationTime(viewModel: IrrigationViewModel){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "مدت آبیاری",
                style = MaterialTheme.typography.subtitle1,
                color = BlueIrrigationDark,
            )
            Icon(Icons.Outlined.Timer, contentDescription = null, tint = BlueIrrigationDark, modifier = Modifier.padding(start = 3.dp))
        }

        OutlinedTextField(
            value = viewModel.irrigationDuration.value.toString() + " ساعت" ,
            onValueChange = {
                viewModel.waterVolume.value = it.toDouble()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 0.dp)
                .background(LightBlue, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = LightBlue,
                textColor = BlueIrrigationDark,
                focusedBorderColor = LightBlue,
                trailingIconColor = BlueIrrigationDark,
                leadingIconColor = BlueIrrigationDark,
                unfocusedBorderColor = LightBlue,
                ),
            leadingIcon = {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.irrigationDuration.value -= 0.5 }
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.irrigationDuration.value += 0.5 }
                )
            },
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = sina,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textDirection = TextDirection.ContentOrRtl,
                textAlign = TextAlign.Center
            ),
        )

    }
}

@Composable
fun IrrigationType(viewModel: IrrigationViewModel){

    var clicked by remember {
        mutableStateOf(false)
    }

    val irrigationTypeList = stringArrayResource(id = R.array.irrigation_type)

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {

        Row(
            Modifier.fillMaxWidth().padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "نوع آبیاری",
                style = MaterialTheme.typography.subtitle1,
                color = BlueIrrigationDark,
            )
            Icon(Icons.Outlined.Shower, contentDescription = null, tint = BlueIrrigationDark, modifier = Modifier.padding(start = 3.dp))
        }

        OutlinedButton(
            onClick = { clicked = !clicked},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = LightBlue,
                contentColor = BlueIrrigationDark
            ),
            border = BorderStroke(1.dp, LightBlue)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()

                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = BlueIrrigationDark,
                    modifier = Modifier
                    .size(40.dp)
                )
                Text(text = viewModel.irrigationType.value, style = MaterialTheme.typography.body1, color = BlueIrrigationDark, modifier = Modifier.padding(end = 20.dp))
            }
        }

        DropdownMenu(
            expanded = clicked,
            onDismissRequest = { clicked = false }
        ) {
            irrigationTypeList.forEach {
                DropdownMenuItem(
                    onClick = {
                        clicked = false
                        viewModel.irrigationType.value = it
                    }
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 20.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }

    }


}

