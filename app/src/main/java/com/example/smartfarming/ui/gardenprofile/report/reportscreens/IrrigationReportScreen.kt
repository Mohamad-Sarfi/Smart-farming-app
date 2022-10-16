package com.example.smartfarming.ui.gardenprofile.report.reportscreens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartfarming.data.room.entities.IrrigationEntity
import com.example.smartfarming.ui.addactivities.ui.theme.BlueIrrigationDark
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.utils.MONTH_LIST

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IrrigationReportScreen(gardenName : String) {
    val viewModel : IrrigationReportViewModel = hiltViewModel()
    var currentMonth by remember {
        mutableStateOf("")
    }

    viewModel.getIrrigationForGarden(gardenName)

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = { ReportFab(color = BlueIrrigationDark) {}}
    ) {
        Column(Modifier.fillMaxSize().background(LightGray2)) {
            TopReportCompose(title = "آرشیو آبیاری باغ "  + gardenName, gardenName, color = BlueIrrigationDark, currentMonth = currentMonth, setCurrentMonth = {currentMonth = it})
            IrrigationReportBody(viewModel)
        }
    }
}

@Composable
fun TopReportCompose(title : String, gardenName: String ,color: Color, currentMonth: String, setCurrentMonth : (String) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(color),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
            Text(text = title, color = Color.White, style = MaterialTheme.typography.body1)
            Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color.White, modifier = Modifier
                .padding(start = 8.dp)
                .size(40.dp))
        }
        Row(modifier = Modifier
            .padding(bottom = 20.dp, start = 20.dp, end = 20.dp, top = 20.dp)
            .horizontalScroll(rememberScrollState())) {
            MONTH_LIST.forEach { month ->
                MonthItem(name = month, currentMonth = currentMonth, color = color){setCurrentMonth(it)}
            }
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
            IrrigationReportCard(viewModel.irrigationList[index])
        }
    }
}

@Composable
fun IrrigationReportCard(irrigationEntity: IrrigationEntity) {
    var clicked by remember {
        mutableStateOf(false)
    }
    val cardHeight by animateDpAsState(
        if (clicked) 160.dp else 120.dp, tween()
    )

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
                Text(text = "14 مهر", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onBackground)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "ساعت " + irrigationEntity.irrigation_duration.toString(), style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
                Text(text = irrigationEntity.irrigation_volume.toString(), style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
            }

            if (clicked){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable {  })
                }
            }
        }
    }
}