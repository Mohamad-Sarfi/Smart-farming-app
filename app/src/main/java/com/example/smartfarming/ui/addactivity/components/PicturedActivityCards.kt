package com.example.smartfarming.ui.addactivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationDark
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple700
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.common_composables.GardenSpinner

@Composable
fun PicturedActivityCards(navController: NavHostController, viewModel: AddActivitiesViewModel, gardenList : List<String>) {

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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        val (gardenSelector, title, cardColumn) = createRefs()

        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
            Text(
                text = "افزودن فعالیت جدید",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(35.dp)
                ,
                color = MaterialTheme.colors.primary
            )
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .constrainAs(cardColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                }) {
            PicturedCard("آبیاری", R.drawable.irrigation_pic, BlueIrrigationDark){
                navController.navigate("${AppScreensEnum.IrrigationScreen.name}/$currentGarden")
            }
            PicturedCard("سمپاشی", R.drawable.irrigation_pic, YellowPesticide){
                navController.navigate("${AppScreensEnum.PesticideScreen.name}/$currentGarden")
            }
            PicturedCard("تغذیه", R.drawable.irrigation_pic, Purple700){
                navController.navigate("${AppScreensEnum.FertilizationScreen.name}/$currentGarden")
            }
            PicturedCard("سایر", R.drawable.irrigation_pic, MainGreen){
                navController.navigate("${AppScreensEnum.OtherActivitiesScreen.name}/$currentGarden")
            }

        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .constrainAs(gardenSelector) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
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