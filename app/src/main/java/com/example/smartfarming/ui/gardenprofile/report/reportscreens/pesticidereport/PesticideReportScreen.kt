package com.example.smartfarming.ui.gardenprofile.report.reportscreens.pesticidereport

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
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.PesticideEntity
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.ReportFab
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.DeleteReportDialog
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.TopReportCompose

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PesticideReportScreen(gardenName : String, navHostController: NavHostController) {
    val viewModel : PesticideReportViewModel = hiltViewModel()
    var currentMonth by remember {
        mutableStateOf("")
    }
    
    viewModel.getPesticideForGarden(gardenName)
    
    Scaffold(
        Modifier
            .fillMaxSize()
            ,
        floatingActionButton = {
            ReportFab(color = YellowPesticide) {
                navHostController.navigate("${AppScreensEnum.PesticideScreen.name}/$gardenName")
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)) {
            TopReportCompose(
                title = "آرشیو سمپاشی",
                gardenName = gardenName,
                icon = Icons.Default.BugReport,
                color = YellowPesticide,
                currentMonth = currentMonth,
                setCurrentMonth = {currentMonth = it}
            )
            PesticideReportBody(viewModel)
        }
    }
}

@Composable
fun PesticideReportBody(viewModel: PesticideReportViewModel) {
    LazyColumn{
        items(viewModel.pesticideList.size){ index ->  
            PesticideReportCard(viewModel.pesticideList[index]){
                viewModel.deletePesticide(it)
            }
        }
    }
}

@Composable
fun PesticideReportCard(pesticideEntity: PesticideEntity, deletePesticide : (PesticideEntity) -> Unit) {
    var clicked by remember {
        mutableStateOf(false)
    }
    val cardHeight by animateDpAsState(
        if (clicked) 160.dp else 120.dp, tween()
    )
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(cardHeight)
        .padding(horizontal = 25.dp, vertical = 8.dp),
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { clicked = !clicked }
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Text(text = pesticideEntity.name, style = MaterialTheme.typography.body1)
            }
            
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "15 مهر", style = MaterialTheme.typography.body2)
                Text(text = pesticideEntity.pesticide_volume.toString(), style = MaterialTheme.typography.body2)
                Text(text = pesticideEntity.pest, style = MaterialTheme.typography.body2)
            }
            if (clicked){
                Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable { showDeleteDialog = true })
                }
            }
        }


        if (showDeleteDialog){
            DeleteReportDialog(setShowDialog = {showDeleteDialog = it}) {
                deletePesticide(pesticideEntity)
            }
        }
    }
}


















