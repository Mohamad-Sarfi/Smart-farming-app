package com.example.smartfarming.ui.gardenprofile.report.reportscreens.otheractivitiesreport

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
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.OtherActivityEntity
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple700
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.ReportFab
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.DeleteReportDialog
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.TopReportCompose

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OtherActivitiesReportScreen(gardenName : String, navHostController: NavHostController) {
    val viewModel : OtherActivitiesReportViewModel = hiltViewModel()
    var currentMonth by remember {
        mutableStateOf("")
    }

    viewModel.getOtherActivitiesForGarden(gardenName)

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            ReportFab(color = MainGreen) {
                navHostController.navigate("${AppScreensEnum.OtherActivitiesScreen.name}/$gardenName")
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)) {
            TopReportCompose(
                title = "آرشیو سایر فعالیتها ",
                gardenName = gardenName,
                icon = Icons.Default.Agriculture,
                color = MainGreen,
                currentMonth = currentMonth,
                setCurrentMonth ={currentMonth = it}
            )
            OtherActivitiesReportBody(viewModel)
        }
    }
}

@Composable
private fun OtherActivitiesReportBody(viewModel: OtherActivitiesReportViewModel) {
    LazyColumn{
        items(viewModel.otherActivitiesList.size){ index ->
            OtherActivitiesReportCard(viewModel.otherActivitiesList[index]){
                viewModel.deleteOtherActivity(it)
            }
        }
    }
}

@Composable
private fun OtherActivitiesReportCard(otherActivityEntity: OtherActivityEntity, deleteOtherAcivity : (OtherActivityEntity) -> Unit) {
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
        Column(Modifier
            .fillMaxSize()
            .clickable { clicked = !clicked }
            .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Text(text = otherActivityEntity.name, style = MaterialTheme.typography.body1)
            }

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "15 مهر", style = MaterialTheme.typography.body2)
                Text(text = "علت: " + otherActivityEntity.cause, style = MaterialTheme.typography.body2)
            }

            if (clicked){
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable { showDeleteDialog = true })
                    Text(text = otherActivityEntity.description, style = MaterialTheme.typography.body2)
                }
            }
        }

        if (showDeleteDialog){
            DeleteReportDialog(setShowDialog = {showDeleteDialog = it}) {
                deleteOtherAcivity(otherActivityEntity)
            }
        }
    }
}












