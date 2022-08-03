package com.example.smartfarming.ui.harvest.harvest_archive

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.harvest.HarvestViewModel

@Composable
fun HarvestBottomSheet(viewModel : HarvestViewModel){
    val yList = listOf<String>("1399", "1400", "1401", "1402")

    Column(
        Modifier
            .height(190.dp)
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

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HarvestTypeSelect("همه", viewModel.selectedType.value){viewModel.selectedType.value = it}
            HarvestTypeSelect("خشک", viewModel.selectedType.value){viewModel.selectedType.value = it}
            HarvestTypeSelect("تازه", viewModel.selectedType.value){viewModel.selectedType.value = it}
        }
    }
}

@Composable
fun HarvestTypeSelect(content: String, selectedType : String, onClick : (String) -> Unit){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(60.dp)
            .height(40.dp)
            .clickable {
                onClick(content)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        backgroundColor = if (selectedType == content) MainGreen else Color.White,
        contentColor = if (selectedType != content) MainGreen else Color.White
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = content, style = MaterialTheme.typography.subtitle1)
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