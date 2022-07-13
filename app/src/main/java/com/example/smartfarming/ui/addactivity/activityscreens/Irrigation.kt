package com.example.smartfarming.ui.addactivity.activityscreens

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigation
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.IrrigationViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.common_composables.ProgressDots
import com.example.smartfarming.ui.common_composables.TitleIcon
import com.example.smartfarming.ui.gardenprofile.ScreensEnumGardenProfile

@Composable
fun Irrigation(
    gardenName: String,
    navController: NavHostController
){

    val Max_STEPS = 1
    val activity = LocalContext.current as Activity

    val viewModel : IrrigationViewModel =
         viewModel(factory = IrrigationViewModelFactory((activity?.application as FarmApplication).repo))

    val garden = viewModel.getGarden(gardenName).observeAsState()
    var irrigationDate = viewModel.irrigationDate
    var irrigationType = viewModel.irrigationType
    val irrigationDuration = viewModel.irrigationDuration

    var step by remember {
        mutableStateOf(0)
    }
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color.White,
                                Color.White,
                                BlueWatering,
                            )
                        )
                )
                .padding(25.dp)

        ) {

            val (main, button, title, progessDots) = createRefs()

            TitleIcon(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 50.dp),
                icon = painterResource(id = R.drawable.irrigation_line1),
                color = BlueIrrigation
            )

            Body(
                modifier = Modifier
                    .constrainAs(main) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(title.bottom)
                    }
                    .fillMaxWidth()
                    .height(400.dp)
                    .graphicsLayer {
                        shadowElevation = 5.dp.toPx()
                        shape = RoundedCornerShape(27.dp)
                        clip = true
                    }
                    .clip(RoundedCornerShape(27.dp))
                    .background(Color.White)
                    .padding(vertical = 70.dp, horizontal = 10.dp),
                garden.value!!,
                irrigationDate,
                irrigationType,
                irrigationDuration,
                setWaterVolume = {viewModel.waterVolume.value = it},
                step
            )

            ProgressDots(
                modifier = Modifier
                    .constrainAs(progessDots) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(main.bottom)

                    }
                    .fillMaxWidth()
                    .padding(10.dp),
                step = step,
                BlueWatering
            )


            Button(
                onClick = {
                    if (step < Max_STEPS){
                        step++
                    } else {
                        viewModel.insertIrrigationDB()
                        navController.navigate(route = ScreensEnumGardenProfile.MainScreen.name){
                            popUpTo(0)
                        }

                    }
                },
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .fillMaxWidth()
                    .padding(2.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = BlueWatering
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = if (step < Max_STEPS) "بعدی" else "تایید",
                    style = MaterialTheme.typography.h5)
            }
        }
    }
}




@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Body(
    modifier: Modifier,
    garden: Garden,
    irrigationDate: MutableState<MutableMap<String,String>>,
    irrigationType: MutableState<String>,
    irrigationDuration : MutableState<Double>,
    setWaterVolume: (Double) -> Unit,
    step : Int
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت آبیاری باغ " + garden.name,
            style = MaterialTheme.typography.h3,
            color = BlueWatering, modifier = Modifier.padding(bottom = 30.dp))

            AnimatedVisibility(
                visible = step == 0,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally() + fadeOut()

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WaterVolume(garden.irrigation_volume){setWaterVolume(it)}
                    IrrigationDuration(irrigationDuration)
                }
            }
            AnimatedVisibility(
                visible = step == 1,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DateSelect(irrigationDate)
                    IrrigationTypeSpinner(irrigationType)
                }
            }

    }
}

@Composable
fun DateSelect(
    irrigationDate : MutableState<MutableMap<String, String>>,
){
    var dialogue by remember {
        mutableStateOf(false)
    }

    Button(
        onClick = { dialogue = !dialogue },
        modifier = Modifier
            .padding(20.dp)
            .width(220.dp)
            .height(60.dp)
            .clip(MaterialTheme.shapes.large)
            .border(2.dp, color = BlueWatering, shape = MaterialTheme.shapes.large),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White
        )
    ) {
        Text(
            text = if (irrigationDate.value["year"] == "") "تاریخ آبیاری" else "${irrigationDate.value["day"]} / ${irrigationDate.value["month"]} / ${irrigationDate.value["year"]}",
            style = MaterialTheme.typography.body2,
            color = BorderGray,
            modifier = Modifier.padding(6.dp)
        )
    }

    if (dialogue){
        DatePicker(openDialogue = dialogue,
            changeOpenDialogue = {dialogue = !dialogue},
            updateDate = {date ->
                irrigationDate.value = date
            })
    }
}

@Composable
fun WaterVolume(
    waterVolume : Double,
    setWaterVolume : (Double) -> Unit
){

    var volume by remember {
        mutableStateOf(waterVolume)
    }

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "حجم آب:",
            style = MaterialTheme.typography.body1,
            color = BlueWatering,
            modifier = Modifier.padding(top= 5.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Remove, contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable {
                        if (volume > 2) volume--
                        setWaterVolume(volume)

                    }
                    .size(35.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(BlueWatering)
                ,
                tint= Color.White
            )

            Row(
                modifier = Modifier.width(120.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = "لیتر", style = MaterialTheme.typography.body1, color = BorderGray, modifier = Modifier)
                Text(text = "$volume", style = MaterialTheme.typography.h4, color = BorderGray,modifier = Modifier.padding(5.dp))
            }

            Icon(Icons.Filled.Add, contentDescription = "", modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable {
                    volume++
                    setWaterVolume(volume)
                }
                .size(35.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(BlueWatering)
                ,
                tint= Color.White
            )
        }
    }
}

@Composable
fun IrrigationDuration(irrigationDuration: MutableState<Double>){

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "مدت آبیاری:",
            style = MaterialTheme.typography.body1,
            color = BlueWatering,
            modifier = Modifier.padding(top= 15.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Remove, contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable {
                        if (irrigationDuration.value > 2.0) {
                            irrigationDuration.value = irrigationDuration.value - 0.5
                        } else {
                            irrigationDuration.value = 0.5
                        }
                    }
                    .size(35.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(BlueWatering)
                ,
                tint= Color.White
            )
            Row(modifier = Modifier
                .width(120.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ساعت", style = MaterialTheme.typography.body1, color = BorderGray,
                    modifier = Modifier
                        .align(CenterVertically))
                Text(text = "${irrigationDuration.value}", style = MaterialTheme.typography.h4, color = BorderGray,modifier = Modifier.padding(5.dp))

            }

            Icon(Icons.Filled.Add, contentDescription = "", modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable { irrigationDuration.value = irrigationDuration.value + 0.5 }
                .size(35.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(BlueWatering)
                ,
                tint= Color.White
            )
        }
    }

}

@Composable
fun IrrigationTypeSpinner(irrigationType: MutableState<String>){

    var expanded by remember {
        mutableStateOf(false)
    }

    var counter by remember {
        mutableStateOf(0)
    }

    val irrigationTypeList = stringArrayResource(id = R.array.irrigation_type)

    Box(
        modifier = Modifier
            .padding(5.dp)
            .width(220.dp)
            .height(60.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(2.dp, color = BlueWatering, shape = MaterialTheme.shapes.large)
            .padding(8.dp)
        ,
    ){
        Row(modifier =
        Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
            ){

            Icon(
                Icons.Default.ArrowDropUp,
                contentDescription = "",
                tint = BlueWatering,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(40.dp)
            )

            Row(modifier =
            Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = if (counter > 0) irrigationType.value else "نوع آبیاری",
                    color = BorderGray,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(2.dp)
                )
            }

        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            irrigationTypeList.forEach {
                DropdownMenuItem(
                    onClick = {
                        counter = 1
                        expanded = false
                        irrigationType.value = it
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