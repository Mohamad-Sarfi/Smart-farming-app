package com.example.smartfarming.ui.addactivity.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Compost
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.common_composables.GardenSpinner

@Composable
fun PicturedActivityCards(navController: NavHostController, gardenList : List<String>) {

    var currentGarden by remember {
        mutableStateOf("انتخاب باغ")
    }
    val context = LocalContext.current
//    if (!viewModel.gardenListState.value.isNullOrEmpty()){
//        currentGarden = viewModel.gardenListState.value!![0].title
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(horizontal = 0.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MainGreen,
                            MainGreen700,
                        )
                    )
                ),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "افزودن فعالیت جدید",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(6.dp),
                    color = MaterialTheme.colors.onPrimary
                )
                Icon(painterResource(id = R.drawable.shovel),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(40.dp))
            }

            Row(
                Modifier
                    .offset(y = 25.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                GardenSpinner(gardenList, currentGarden, 1f){
                    currentGarden = it
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 50.dp)
                .verticalScroll(rememberScrollState())
        ) {
            PicturedCard("آبیاری", R.drawable.irrigation_pic, BlueIrrigationDark, Icons.Default.WaterDrop){
                checkIfGardenSelected(currentGarden, context){
                    navController.navigate("${AppScreensEnum.IrrigationScreen.name}/$currentGarden")
                }
            }
            PicturedCard("سمپاشی", R.drawable.activities_pic, YellowPesticide, Icons.Default.BugReport){
                checkIfGardenSelected(currentGarden, context){
                    Log.i("TAG start screen", "${AppScreensEnum.PesticideScreen.name}/$currentGarden")
                    navController.navigate("${AppScreensEnum.PesticideScreen.name}/$currentGarden")
                }
            }
            PicturedCard("تغذیه", R.drawable.fertilizaiton_pic, Purple700, Icons.Default.Compost){
                checkIfGardenSelected(currentGarden, context){
                    navController.navigate("${AppScreensEnum.FertilizationScreen.name}/$currentGarden")
                }
            }
            PicturedCard("سایر", R.drawable.prune_pic, MainGreen, Icons.Default.Agriculture){
                checkIfGardenSelected(currentGarden, context){
                    navController.navigate("${AppScreensEnum.OtherActivitiesScreen.name}/$currentGarden")
                }
            }
        }
    }
}

private fun checkIfGardenSelected(currentGarden : String, context: Context, navigate : () -> Unit){
    /**
     * checks if garden is selected, then navigates to the next screen (provided in lambda).
     * otherwise shows a toast
     */

    if (currentGarden.equals("انتخاب باغ")){
        Toast.makeText(context, "ابتدا باغ مورد نظر را انتخاب کنید", Toast.LENGTH_SHORT).show()
    } else {
        navigate()
    }
}