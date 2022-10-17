package com.example.smartfarming.ui.gardenprofile.report.reportscreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.IrrigationEntity
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationDark
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.DeleteReportDialog
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.composables.TopReportCompose
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.irrigationreport.IrrigationReportViewModel
import com.example.smartfarming.utils.PersianCalender

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IrrigationReportScreen(gardenName : String, navHostController: NavHostController) {
    val viewModel : IrrigationReportViewModel = hiltViewModel()
    var currentMonth by remember {
        mutableStateOf("")
    }

    viewModel.getIrrigationForGarden(gardenName)

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = { ReportFab(color = BlueIrrigationDark) {navHostController.navigate("${AppScreensEnum.IrrigationScreen.name}/$gardenName")}}
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2)) {
            TopReportCompose(title = "آرشیو آبیاری باغ",
                gardenName,
                icon = Icons.Default.WaterDrop,
                color = BlueIrrigationDark,
                currentMonth = currentMonth,
                setCurrentMonth = {currentMonth = it})
            IrrigationReportBody(viewModel)
        }
    }
}


@Composable
fun MonthItem(name : String, currentMonth : String , color: Color,clickListener : (String) -> Unit) {
    Card(
        modifier = Modifier.padding(5.dp),
        elevation = if (name == currentMonth) 5.dp else 2.dp,
        backgroundColor = if (name == currentMonth) color else Color.White,
        border = if (name == currentMonth) BorderStroke(2.dp, Color.White) else BorderStroke(0.dp, Color.White)
    ) {
        Column(
            modifier = Modifier
                .width(65.dp)
                .height(40.dp)
                .clickable { clickListener(name) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name, color = if (name == currentMonth) Color.White else color, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun IrrigationReportBody(viewModel: IrrigationReportViewModel) {
    LazyColumn{
        items(viewModel.irrigationList.size){ index ->
            IrrigationReportCard(viewModel.irrigationList[index]){
                viewModel.deleteIrrigation(it)
                viewModel.irrigationList.remove(it)
            }
        }
    }
}

@Composable
fun IrrigationReportCard(irrigationEntity: IrrigationEntity, deleteIrrigation : (IrrigationEntity) -> Unit) {
    var clicked by remember {
        mutableStateOf(false)
    }
    val cardHeight by animateDpAsState(
        if (clicked) 160.dp else 120.dp, tween()
    )
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var monthName = ""

    if (!irrigationEntity.date.isNullOrEmpty()){
        monthName = PersianCalender.getMonthNameFromNum(irrigationEntity.date.split("/")[1].toInt())
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
                .padding(20.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                Text(text =irrigationEntity.date.split("/")[2] + "  ${monthName}", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onBackground)

            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "ساعت " + irrigationEntity.irrigation_duration.toString(), style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
                Text(text = irrigationEntity.irrigation_volume.toString() + "  لیتر", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
            }

            if (clicked){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable { showDeleteDialog = true })
                }
            }

            if (showDeleteDialog){
                DeleteReportDialog(setShowDialog = {showDeleteDialog = it}) {
                    deleteIrrigation(irrigationEntity)
                }
            }
        }
    }
}