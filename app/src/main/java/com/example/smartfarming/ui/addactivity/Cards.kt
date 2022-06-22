package com.example.smartfarming.ui.addactivity

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.common_composables.GardenSpinner

@Composable
fun Cards(
    navController: NavHostController,
    gardenList : List<String>
){
    val context = LocalContext.current
    var currentGarden by remember {
        if (gardenList.isNotEmpty()){
            mutableStateOf(gardenList[0])
        }
        else {
            mutableStateOf("انتخاب باغ")
        }
    }



    var clicked = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
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
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Card("آبیاری",  R.drawable.watering, BlueWatering) {

                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate("${AppScreensEnum.IrrigationScreen.name}/$currentGarden")
                }
            }
            Card("تغذیه", R.drawable.npk, PurpleFertilizer) {

                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate("${AppScreensEnum.FertilizationScreen.name}/${currentGarden}")
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Card("سم پاشی", R.drawable.pesticide1, YellowPesticide){

                if (currentGarden == "انتخاب باغ"){
                    Toast.makeText(context, "ابتدا باغ را انتخاب کنید", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(route = "${AppScreensEnum.PesticideScreen.name}/${currentGarden}")
                }
            }
            Card("سایر", R.drawable.shovel, MaterialTheme.colors.primary) {
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
                }

            }
        }
    }
}