package com.example.smartfarming.ui.gardenprofile.report

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(viewModel: ReportViewModel) {

    val yList = listOf<String>("1399", "1400", "1401", "1402")

    Column(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(LightBackground)
            .padding(top = 35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow {
            items(yList) {
                YearCard(content = it, selectedYear = viewModel.selectedYear.value){
                    viewModel.selectedYear.value = it
                }
            }
        }
    }
}

@Composable
fun YearCard(content : String, selectedYear : String ,onClick : (String) -> Unit){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(55.dp)
            .height(70.dp)
            .clickable {
                onClick(content)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        backgroundColor = if (selectedYear == content) MainGreen else Color.White,
        contentColor = if (selectedYear != content) MainGreen else Color.White
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = content, style = MaterialTheme.typography.subtitle1)
        }
    }
}