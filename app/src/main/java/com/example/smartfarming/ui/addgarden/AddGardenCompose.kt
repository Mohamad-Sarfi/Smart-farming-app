package com.example.smartfarming.ui.addgarden

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smartfarming.MainActivity
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun AddGardenCompose(
    viewModel: AddGardenViewModel,
    navController: NavHostController
){
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var step = viewModel.step
    val isLocationSet by viewModel.isLocationSet
    val latLong by viewModel.location.observeAsState()
    val gardenArea by viewModel.gardenArea

    val gardenAge by viewModel.gardenAge

    val varietiesList = viewModel.typeArray

    val irrigationDuration by viewModel.irrigationDuration
    val irrigationVolume by viewModel.irrigationVolume
    val soilType by viewModel.soilType.observeAsState()



    // Animation transitions
    var step1Transition by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(1f),
        backgroundColor = MainGreen
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f)
            .fillMaxSize(1f)) {

            val (topCard, side ,middle, button) = createRefs()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .constrainAs(topCard) {
                        top.linkTo(parent.top, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
            ) {
                Column {
                    ManageTopCard(step = step.value!!){viewModel.decideIconSource(step.value!!)}
                }
            }

            StepsColumn(modifier = Modifier
                .constrainAs(side) {
                    start.linkTo(parent.start)
                    top.linkTo(topCard.bottom)
                    bottom.linkTo(parent.bottom)
                }
                .padding(
                    start = 25.dp,
                    end = 0.dp,
                    bottom = 60.dp
                ),
                step = step
            )

            //***************************** Main part **********************************************
            Row(modifier = Modifier
                .constrainAs(middle) {
                    end.linkTo(parent.end)
                    top.linkTo(topCard.bottom)
                    bottom.linkTo(button.top)
                    start.linkTo(side.end)
                }
                .fillMaxWidth(.8f)
                .fillMaxHeight(0.7f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Crossfade(
                    targetState = step.value,
                    animationSpec = tween(1000)
                ) {
                    when(it){
                        1 ->
                            AddGardenStep1(viewModel)
                        2 ->
                            AddGardenStep2(
                                viewModel
                            )
                        3 -> AddGardenStep3(navController, viewModel)
                        else -> AddGardenStep4()

                    }
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, margin = 15.dp)
                    }
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = {
                        if (step.value == 1){
                            activity.finish()
                        } else {
                            viewModel.decrementStep()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MainGreen,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .padding(5.dp)
                        .height(60.dp)
                        .fillMaxWidth(0.3f),
                    border = BorderStroke(2.dp, Color.White)
                )
                {
                    Text(
                        text = if (step.value == 1) "بازگشت" else "قبلی",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(
                            vertical = 2.dp
                        )
                    )
                }

                Button(
                    onClick = {
                        if (step.value != viewModel.MAX_STEPS){
                            handleIncrementButton(viewModel, context)
                        } else {
                            val garden = Garden(
                                0,
                                viewModel.gardenName.value,
                                gardenAge!!.toInt(),
                                "${latLong!!["lat"]} - ${latLong!!["long"]}",
                                "pistachios",
                                varietiesList.value.joinToString(","),
                                "Ros",
                                "classic",
                                irrigationDuration!!.toDouble(),
                                irrigationVolume!!.toDouble(),
                                viewModel.irrigationCycle.value.toInt(),
                                gardenArea!!.toDouble(),
                                0
                            )
                            viewModel.addGardenToDb(garden)
                            activity.finish()
                        }
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(60.dp)
                        .fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = if (step.value!! < viewModel.MAX_STEPS) "ادامه" else "ثبت",
                        style = MaterialTheme.typography.h5,
                        color = MainGreen,
                        modifier = Modifier.padding(
                            vertical = 2.dp
                        )
                    )
                }
            }
        }

    }
}



@Composable
fun StepCircle(
    step : Int,
    numberTag: Int
){
    val circle1Animation by animateDpAsState(
        targetValue = if (step == numberTag) 38.dp else 10.dp
    )
    val circleColor by animateColorAsState(
        if (step == numberTag) Color.White else LightGray
    )
    Column(modifier = Modifier
        .wrapContentSize(Alignment.Center)
        .padding(vertical = 18.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .size(width = 10.dp, height = circle1Animation)
                .clip(RoundedCornerShape(30.dp))
                .background(circleColor)
                .padding(14.dp)
        )
    }
}

@Composable
fun ManageTopCard(step: Int, decideIcon : (Int) -> ImageVector){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            decideIcon(step),
            contentDescription = "",
            modifier = Modifier
                .padding(20.dp)
                .size(
                    120.dp
                ),
            tint = Color.White
        )

        Crossfade(
            targetState = step
        ) { screenStep ->
            when(screenStep){
                1 ->
                    Text(
                    text = "ثبت باغ جدید",
                    style = MaterialTheme.typography.h4, modifier = Modifier
                        .padding(8.dp),
                    color = Color.White)
                2 ->
                    Text(
                        text = "اطلاعات آبیاری باغ",
                        style = MaterialTheme.typography.h4, modifier = Modifier
                            .padding(8.dp),
                        color = Color.White
                    )
                3 ->
                    Text(
                        text = "موقعیت مکانی باغ",
                        style = MaterialTheme.typography.h4, modifier = Modifier
                            .padding(8.dp),
                        color = Color.White
                    )
                4 ->
                    Text(
                        text = "نتایج آزمایش آب و خاک",
                        style = MaterialTheme.typography.h4, modifier = Modifier
                            .padding(8.dp),
                        color = Color.White
                    )
            }
        }
    }
    
}

@Composable
fun StepsColumn(
    modifier: Modifier,
    step: State<Int?>
){
    Column(
        modifier = modifier
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepCircle(step = step.value!!, 1)
        StepCircle(step = step.value!!, 2)
        StepCircle(step = step.value!!, 3)
        StepCircle(step = step.value!!, 4)
    }
}

fun handleIncrementButton(viewModel: AddGardenViewModel, context: Context){
    when(viewModel.step.value){
        1 ->
            if (viewModel.checkFirstStep()){
                viewModel.incrementStep()
            } else {
                Toast.makeText(context, "تمام فیلدها را کامل کنید", Toast.LENGTH_SHORT).show()
            }
        2 ->
            if (viewModel.checkSecondStep()){
                viewModel.incrementStep()
            } else {
                Toast.makeText(context, "تمام فیلدها را کامل کنید", Toast.LENGTH_SHORT).show()
            }
        3 ->
            if (viewModel.checkThirdStep()){
                viewModel.incrementStep()
            } else {
                Toast.makeText(context, "تمام فیلدها را کامل کنید", Toast.LENGTH_SHORT).show()
            }

    }
}