package com.example.smartfarming.ui.addactivity.activityscreens.irrigation

import android.app.Activity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationLight
import com.example.smartfarming.ui.addactivities.ui.theme.LightBlue
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModelFactory


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
                    .padding(top = 30.dp, bottom = 10.dp, end = 40.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "باغ " +  gardenName, style = MaterialTheme.typography.h5, color = BlueIrrigation, modifier = Modifier.padding(end = 15.dp))
                Text(text = "آبیاری", style = MaterialTheme.typography.h3, color = BlueIrrigation)
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

    var dialog by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End) {

            Text(text = "تاریخ آبیاری", style = MaterialTheme.typography.body1, color = BlueIrrigation, modifier = Modifier.padding(bottom = 15.dp))

            OutlinedButton(
                onClick = {
                          dialog = !dialog
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.dp, BlueIrrigationLight),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = LightBlue,
                    contentColor = BlueIrrigation
                )
            ) {
                Text(
                    text =if (viewModel.irrigationDate.value["year"] == "") "" else "${viewModel.irrigationDate.value["year"]}/${viewModel.irrigationDate.value["month"]}/${viewModel.irrigationDate.value["day"]}",
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
}

@Composable
fun StepManager(step : Int){

    val firstWidth by animateDpAsState(
        if (step == 0) 140.dp else 80.dp
    )
    val secondtWidth by animateDpAsState(
        if (step == 1) 140.dp else 80.dp
    )

    Row(
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .padding(8.dp)
            .width(firstWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 0) BlueIrrigation else BlueIrrigationLight)

        )
        Box(modifier = Modifier
            .padding(8.dp)
            .width(secondtWidth)
            .height(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (step == 1) BlueIrrigation else BlueIrrigationLight)


        )

    }
}