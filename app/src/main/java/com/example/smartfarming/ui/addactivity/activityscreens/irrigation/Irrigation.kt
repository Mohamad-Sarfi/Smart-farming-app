package com.example.smartfarming.ui.addactivity.activityscreens.irrigation

import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.sina


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
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 5.dp, end = 40.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "باغ " +  gardenName, style = MaterialTheme.typography.h5, color = BlueIrrigationDark, modifier = Modifier.padding(end = 10.dp))
                Text(text = "آبیاری", style = MaterialTheme.typography.h3, color = BlueIrrigationDark)
                Icon(Icons.Outlined.WaterDrop, contentDescription = null, modifier = Modifier.padding(5.dp).size(40.dp), tint = BlueIrrigationDark)
            }

            StepManager(viewmodel.step.value)
            IrrigationBody(viewmodel)
        }
    }
}

@Composable
fun IrrigationBody(viewModel: IrrigationViewModel){
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            ,
        shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
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

    AnimatedVisibility(
        viewModel.step.value == 0
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IrrigationDate(viewModel)
            IrrigationVolume(viewModel)
            IrrigationTime(viewModel)
        }
    }

    AnimatedVisibility(visible = viewModel.step.value == 1) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            IrrigationType(viewModel)

            IrrigationWorker(viewModel)
        }
    }

}

@Composable
fun StepManager(step : Int){

    val firstWidth by animateDpAsState(
        if (step == 0) 170.dp else 80.dp
    )
    val secondtWidth by animateDpAsState(
        if (step == 1) 170.dp else 80.dp
    )

    Row(
        Modifier
            .padding(vertical = 5.dp, horizontal = 40.dp)
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Box(modifier = Modifier
            .padding(8.dp)
            .width(firstWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 0) BlueIrrigationDark else BlueIrrigationLight)

        )
        Box(modifier = Modifier
            .padding(8.dp)
            .width(secondtWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 1) BlueIrrigationDark else BlueIrrigationLight)


        )

    }
}

@Composable
fun IrrigationVolume(viewModel: IrrigationViewModel){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Text(text = "حجم آب", style = MaterialTheme.typography.subtitle1, color = BlueIrrigation, modifier = Modifier.padding(bottom = 15.dp))

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
               textColor = BlueIrrigation,
               focusedBorderColor = BlueIrrigationLight,
               trailingIconColor = BlueIrrigation,
               leadingIconColor = BlueIrrigation,
                unfocusedBorderColor = BlueIrrigationLight,


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
fun IrrigationDate(viewModel: IrrigationViewModel){

    var dialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {

        Text(text = "تاریخ آبیاری", style = MaterialTheme.typography.subtitle1, color = BlueIrrigation, modifier = Modifier.padding(bottom = 15.dp))

        OutlinedButton(
            onClick = {
                dialog = !dialog
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, BlueIrrigationLight),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = LightBlue,
                contentColor = BlueIrrigation
            )
        ) {
            Text(
                text =if (viewModel.irrigationDate.value["year"] == "") "تاریخ آبیاری" else "${viewModel.irrigationDate.value["year"]}/${viewModel.irrigationDate.value["month"]}/${viewModel.irrigationDate.value["day"]}",
                style = MaterialTheme.typography.body1
            )
        }

        if (dialog){
            DatePicker(openDialogue = dialog,
                changeOpenDialogue = {dialog = !dialog},
                updateDate = {date ->
                    viewModel.irrigationDate.value = date
                })
        }

    }
}

@Composable
fun IrrigationTime(viewModel: IrrigationViewModel){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Text(text = "مدت آبیاری", style = MaterialTheme.typography.subtitle1, color = BlueIrrigation, modifier = Modifier.padding(bottom = 15.dp))

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
                textColor = BlueIrrigation,
                focusedBorderColor = BlueIrrigationLight,
                trailingIconColor = BlueIrrigation,
                leadingIconColor = BlueIrrigation,
                unfocusedBorderColor = BlueIrrigationLight,
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
        Text(
            text = "نوع آبیاری",
            style = MaterialTheme.typography.subtitle1,
            color = BlueIrrigation,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        OutlinedButton(
            onClick = { clicked = !clicked},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = LightBlue,
                contentColor = BlueIrrigation
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
                    tint = BlueIrrigation,
                    modifier = Modifier
                    .size(40.dp)
                )
                Text(text = viewModel.irrigationType.value, style = MaterialTheme.typography.body1, color = BlueIrrigation, modifier = Modifier.padding(end = 20.dp))
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

@Composable
fun IrrigationWorker(viewModel: IrrigationViewModel){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        Text(text = "تعداد کارگر", style = MaterialTheme.typography.subtitle1, color = BlueIrrigation, modifier = Modifier.padding(bottom = 15.dp))

        OutlinedTextField(
            value = viewModel.irrigationWorkers.value.toString() + " نفر" ,
            onValueChange = {
                viewModel.irrigationWorkers.value = it.toInt()
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
                textColor = BlueIrrigation,
                focusedBorderColor = BlueIrrigationLight,
                trailingIconColor = BlueIrrigation,
                leadingIconColor = BlueIrrigation,
                unfocusedBorderColor = LightBlue,


                ),
            leadingIcon = {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { if (viewModel.irrigationWorkers.value > 1) viewModel.irrigationWorkers.value -= 1 }
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.irrigationWorkers.value += 1 }
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