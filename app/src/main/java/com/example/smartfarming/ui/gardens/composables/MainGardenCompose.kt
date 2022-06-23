package com.example.smartfarming.ui.gardens.composables

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addgarden.AddGarden
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.gardens.GardensViewModel
import com.example.smartfarming.ui.gardens.GardensViewModelFactory
import com.example.smartfarming.ui.home.composables.MyFAB


@Composable
fun GardenCompose(){


    val activity = LocalContext.current as Activity
    val viewModel : GardensViewModel = viewModel(
        factory = GardensViewModelFactory((activity.application as FarmApplication).repo)
    )

    val gardenList by viewModel.getGardens().observeAsState()
    val context = LocalContext.current


    var fabExtended by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
        },
        floatingActionButton = {
            MyFAB(context = context, fabExtended = fabExtended) {
                fabExtended =! fabExtended
            }
        }
    ) {
        Column() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "باغ های شما",
                    style = MaterialTheme.typography.h3,
                    color = MainGreen
                )

                Icon(
                    Icons.Default.Eco,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(50.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.verticalScroll(rememberScrollState())
                    .weight(1f, false)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (gardenList.isNullOrEmpty()){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "",
                            tint = YellowPesticide,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(50.dp)

                        )

                        Text(
                            text = "هنوز باغی وارد نشده!",
                            style = MaterialTheme.typography.body2,
                            color = BorderGray
                        )

                    }
                } else {
                    LazyColumn{
                        items(gardenList!!){
                            GardenCard(garden = it)
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ActivitySticker(job : String){

    val activities = stringArrayResource(id = R.array.garden_activities)

    Box(modifier = Modifier
        .clip(RoundedCornerShape(100))
        .background(
            when (job) {
                activities[0] -> blueIrrigation
                activities[1] -> redFertilizer
                activities[2] -> greenPesticide
                activities[3] -> Purple500
                else -> MainGreen
            }
        )
        .size(55.dp)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun showGardenCard(){
    GardenCard(garden = Garden(
        0,
        "اکبری", 20, "25", "s", "", "",
        "",
        5.5,
    5.5,
        5.5,
        0
        )
    )
}