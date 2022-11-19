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
import com.example.smartfarming.utils.YEARS_LIST

@Composable
fun BottomSheetContent(viewModel: ReportViewModel) {
    val yList = YEARS_LIST

    Column(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(LightBackground)
            .padding(top = 35.dp, end = 10.dp, start = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow {
            items(yList) {
                YearCard(content = it, selectedYear = viewModel.selectedYear.value){ year ->
                    viewModel.selectedYear.value = year
                }
            }
        }
    }
}

@Composable
private fun YearCard(content : String, selectedYear : String ,onClick : (String) -> Unit){
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