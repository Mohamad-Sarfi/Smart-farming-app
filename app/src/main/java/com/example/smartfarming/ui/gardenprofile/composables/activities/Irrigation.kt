package com.example.smartfarming.ui.gardenprofile.composables.activities

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.Screens.DateSpinner
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.adduser.ui.theme.BlueWatering
import com.example.smartfarming.ui.gardenprofile.GardenProfileViewModel
import com.example.smartfarming.ui.gardenprofile.ScreensEnumGardenProfile

@Composable
fun Irrigation(
    gardenName: String,
    viewModel: GardenProfileViewModel,
    navController: NavHostController
){
    val garden = viewModel.getGarden(gardenName).observeAsState()
    var irrigationDate = remember {
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))
    }
    var irrigationType = remember {
        mutableStateOf(garden.value!!.irrigation_type)
    }

    val irrigationDuration = remember {
        mutableStateOf(garden.value!!.irrigation_duration)
    }

    var fertilizer by remember {
        mutableStateOf("")
    }
    
    Scaffold(
        modifier = Modifier
            .background(BlueWatering)
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (main, button) = createRefs()

            Column(modifier = Modifier
                .constrainAs(main) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy((-32.dp))
            ) {
                TitleBar()
                Body(garden.value!!, irrigationDate, irrigationType, irrigationDuration)
            }
            Button(
                onClick = {
                    navController.navigate(route = ScreensEnumGardenProfile.MainScreen.name)
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
                    backgroundColor = BlueWatering,
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "تایید", style = MaterialTheme.typography.h5)
            }
        }
    }
}

@Composable
fun TitleBar(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueWatering)
                .padding(top = 30.dp, bottom = 50.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.irrigation_line1),
                contentDescription = "",
                modifier = Modifier
                    .padding(10.dp)
                    .size(60.dp),
                tint = Color.White
            )
            //Text(text = "ثبت آبیاری", style = MaterialTheme.typography.h5, color = Color.White)
    }
}

@Composable
fun Body(
    garden: Garden,
    irrigationDate: MutableState<MutableMap<String,String>>,
    irrigationType: MutableState<String>,
    irrigationDuration : MutableState<Double>
){
    Column(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ثبت آبیاری باغ " + garden.name, style = MaterialTheme.typography.h3, color = BlueWatering, modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp))
        WaterVolume(garden.irrigation_volume)
        IrrigationDuration(irrigationDuration)
        DateSelect(irrigationDate)
        IrrigationTypeSpinner(irrigationType)


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
fun WaterVolume(waterVolume : Double){

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
                    .clickable { if (volume > 2) volume-- else 1 }
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
                .clickable { volume++ }
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