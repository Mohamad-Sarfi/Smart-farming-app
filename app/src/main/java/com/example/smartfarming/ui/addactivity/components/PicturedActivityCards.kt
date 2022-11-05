package com.example.smartfarming.ui.addactivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

    var currentGarden by remember {
        mutableStateOf("انتخاب باغ")
    }

    var gardenId by remember {
        mutableStateOf(0)
    }

    if (!viewModel.gardenListState.value.isNullOrEmpty()){
        currentGarden = viewModel.gardenListState.value!![0].title
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
                .padding(0.dp)
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
                    .padding(6.dp)
                ,
                color = MaterialTheme.colors.primary
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .constrainAs(gardenSelector) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GardenSpinner(gardenList, currentGarden){
                currentGarden = it
                viewModel.gardenListState.value?.forEach { g ->
                    if (g.title == it){
                        gardenId = g.id
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState())
                .constrainAs(cardColumn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(gardenSelector.bottom)
                }) {
            PicturedCard("آبیاری", R.drawable.irrigation_pic, BlueIrrigationDark, Icons.Default.WaterDrop){
                navController.navigate("${AppScreensEnum.IrrigationScreen.name}/$currentGarden")
            }
            PicturedCard("سمپاشی", R.drawable.activities_pic, YellowPesticide, Icons.Default.BugReport){
                navController.navigate("${AppScreensEnum.PesticideScreen.name}/$currentGarden")
            }
            PicturedCard("تغذیه", R.drawable.fertilizaiton_pic, Purple700, Icons.Default.Compost){
                navController.navigate("${AppScreensEnum.FertilizationScreen.name}/$currentGarden")
            }
            PicturedCard("سایر", R.drawable.prune_pic, MainGreen, Icons.Default.Agriculture){
                navController.navigate("${AppScreensEnum.OtherActivitiesScreen.name}/$currentGarden")
            }
        }
    }
}