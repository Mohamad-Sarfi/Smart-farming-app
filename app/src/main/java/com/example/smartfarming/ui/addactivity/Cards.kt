package com.example.smartfarming.ui.addactivity

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Compost
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.common_composables.GardenSpinner

@Composable
fun Cards(
    navController: NavHostController,
    viewModel: AddActivitiesViewModel,
    gardenList : List<String>
){
    val context = LocalContext.current

    val gardenListState = viewModel.getGardens().observeAsState()

    var currentGarden by remember {
        if (!gardenListState.value.isNullOrEmpty()){
            mutableStateOf(gardenList[0])
        }
        else {
            mutableStateOf("انتخاب باغ")
        }
    }

    var gardenId by remember {
        mutableStateOf(0)
    }




    var clicked = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LightBackground)
            .padding(top = 50.dp)
    ) {
        Text(
            text = "افزودن فعالیت جدید",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(35.dp)
            ,
            color = MaterialTheme.colors.primary
        )
        Row(
            modifier = Modifier
                .padding(0.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Card("آبیاری",  Icons.Default.WaterDrop, BlueWatering) {

                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate("${AppScreensEnum.IrrigationScreen.name}/$currentGarden")
                }
            }
            Card("تغذیه", Icons.Default.Compost, PurpleFertilizer) {

                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    Log.i("xxxxx", "${AppScreensEnum.FertilizationScreen.name}/${gardenId}")
                    navController.navigate("${AppScreensEnum.FertilizationScreen.name}/${gardenId}")
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Card("سم پاشی", Icons.Default.PestControl, YellowPesticide){

                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(route = "${AppScreensEnum.PesticideScreen.name}/${currentGarden}")
                }
            }
            Card("سایر", Icons.Default.Agriculture, MaterialTheme.colors.primary) {
                val act = "act"
                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else{
                    navController.navigate(route = "${AppScreensEnum.OtherActivitiesScreen.name}/$currentGarden/$act")
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Column() {
                Text(
                    text = "باغ مورد نظر را انتخاب کنید",
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.body1
                )
                GardenSpinner(gardenList, currentGarden){
                    currentGarden = it
                    gardenListState.value?.forEach { g ->
                        if (g.name == it){
                            gardenId = g.id
                        }
                    }
                }

            }
        }
    }
}