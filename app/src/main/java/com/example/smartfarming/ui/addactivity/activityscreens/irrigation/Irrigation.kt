package com.example.smartfarming.ui.addactivity.activityscreens.irrigation

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.ActivityTitle
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.DateSelector
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.SuccessCompose
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.WorkerNumber
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.ActivitiesStepBars
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Irrigation(gardenName : String, navHostController: NavHostController){

    val activity = LocalContext.current as Activity
    val viewmodel : IrrigationViewModel = hiltViewModel()
    var step = viewmodel.step
    var startup by remember {
        mutableStateOf(false)
    }
    val garden = viewmodel.getGarden(gardenName)

    LaunchedEffect(key1 = null) {
        delay(100)
        startup = true
    }
    Scaffold(
        Modifier
            .background(Blue500)
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Blue500),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ActivityTitle(gardenName = gardenName, activityName = "آبیاری", icon =Icons.Default.WaterDrop, Color.White)
            ActivitiesStepBars(viewmodel.step.value, Color.White, Color.White.copy(.6f))
            AnimatedVisibility(visible = startup) {
                IrrigationBody(viewmodel, navHostController)
            }
        }
    }
}

@Composable
fun IrrigationBody(viewModel: IrrigationViewModel, navHostController: NavHostController){
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .fillMaxHeight(.85f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 3.dp
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (bottomRow, body, backgroundPic) = createRefs()

            Icon(
                Icons.Outlined.WaterDrop,
                contentDescription = null,
                tint = Blue100.copy(.1f),
                modifier = Modifier
                    .padding(bottom = 1.dp)
                    .size(300.dp)
                    .constrainAs(backgroundPic) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .zIndex(.5f)
            )

            Column(
                Modifier
                    .constrainAs(body) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .zIndex(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IrrigationBody1(viewModel = viewModel, navHostController)
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
                    onClick = {
                        if ( viewModel.step.value == 0){
                            navHostController.popBackStack()
                        }
                        viewModel.decreaseStep() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue500,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .height(55.dp)
                        .fillMaxWidth(0.2f)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null )
                }

                Button(
                    onClick = {
                        if (viewModel.step.value == 0){
                            viewModel.increaseStep()
                        } else {
                            viewModel.submitClickHandler()
                        }
                              },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue500,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(0.9f)
                        .height(55.dp)
                ) {
                    Text(text = if (viewModel.step.value == 0 ) "بعدی" else "ثبت اطلاعات", style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}

@Composable
fun IrrigationBody1(viewModel: IrrigationViewModel, navHostController: NavHostController){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Crossfade(
        viewModel.step.value,
        animationSpec = tween(1000)
    ) { stepPage ->
        when (stepPage){
            0 ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (viewModel.dateNotSet.value){
                        Toast.makeText(context, "تاریخ را تعیین کنید", Toast.LENGTH_SHORT).show()
                    }

                    DateSelector(
                        "آبیاری",
                        date = viewModel.irrigationDate.value,
                        BlueIrrigationDark,
                        Blue50){
                        viewModel.setIrrigationDate(it)
                    }

                    IrrigationVolume(viewModel)

                    IrrigationTime(viewModel)
                }
            1 ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    IrrigationType(viewModel)

                    WorkerNumber(
                        viewModel.step.value,
                        BlueIrrigationDark,
                        Blue50
                    ){
                        viewModel.irrigationWorkers.value = it
                    }
                }
            2 -> SuccessCompose(navHostController)
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
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
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
                .background(Blue50, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Blue50,
                textColor = BlueIrrigationDark,
                focusedBorderColor = Blue50,
                trailingIconColor = BlueIrrigationDark,
                leadingIconColor = BlueIrrigationDark,
                unfocusedBorderColor = Blue50,
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
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
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
                .background(Blue50, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Blue50,
                textColor = BlueIrrigationDark,
                focusedBorderColor = Blue50,
                trailingIconColor = BlueIrrigationDark,
                leadingIconColor = BlueIrrigationDark,
                unfocusedBorderColor = Blue50,
                ),
            leadingIcon = {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.irrigationDuration.value -= 1 }
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { viewModel.irrigationDuration.value += 1 }
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
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
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
                backgroundColor = Blue50,
                contentColor = BlueIrrigationDark
            ),
            border = BorderStroke(1.dp, Blue50)
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

