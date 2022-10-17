package com.example.smartfarming.ui.gardenprofile.report.reportscreens.fertilizationreport

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compost
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.FertilizationEntity
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.addactivities.ui.theme.Purple700
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.ReportFab
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.DeleteReportDialog
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.TopReportCompose

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FertilizationReportScreen(gardenName : String, navHostController: NavHostController) {
    val viewModel : FertilizationReportViewModel = hiltViewModel()
    var currentMonth by remember {
        mutableStateOf("")
    }

    viewModel.getFertilizationsForGarden(gardenName)

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            ReportFab(color = Purple700) {
                navHostController.navigate("${AppScreensEnum.FertilizationScreen.name}/$gardenName")
            }}
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)) {
            TopReportCompose(
                title = "آرشیو تغذیه باغ",
                gardenName = gardenName,
                icon = Icons.Default.Compost,
                color = Purple700,
                currentMonth = currentMonth,
                setCurrentMonth = {currentMonth = it}
            )
            FertilizationReportBody(viewModel)
        }
    }
}

@Composable
fun FertilizationReportBody(viewModel: FertilizationReportViewModel) {
    LazyColumn{
        items(viewModel.fertilizationList.size){ index ->
            FertilizationReportCard(viewModel.fertilizationList[index]){
                viewModel.deleteFertilization(it)
                viewModel.fertilizationList.remove(it)
            }
        }
    }
}

@Composable
fun FertilizationReportCard(fertilizationEntity: FertilizationEntity, deleteFertilization : (FertilizationEntity) -> Unit) {
    var clicked by remember {
        mutableStateOf(false)
    }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val cardHeight by animateDpAsState(
        if (clicked) 160.dp else 120.dp, tween()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(horizontal = 25.dp, vertical = 8.dp),
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .clickable { clicked = !clicked }
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Text(text = fertilizationEntity.name, style = MaterialTheme.typography.body1)
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "15 مهر", style = MaterialTheme.typography.body2)
                Text(text = fertilizationEntity.volume.toString(), style = MaterialTheme.typography.body2)
                Text(text = fertilizationEntity.fertilization_type, style = MaterialTheme.typography.body2)
            }

            if (clicked){
                Row(
                    Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable { showDeleteDialog = true })
                }
            }

            if (showDeleteDialog){
                DeleteReportDialog(setShowDialog = {showDeleteDialog = it}) {
                    deleteFertilization(fertilizationEntity)
                }
            }
        }
    }
}















