package com.example.smartfarming.ui.gardenprofile.report

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import java.time.Year

@Composable
fun HarvestGraph(
    navController: NavHostController,
    gardenName : String
){
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(260.dp)
            .clickable {
                navController.navigate(route = "${AppScreensEnum.GardenHarvestScreen.name}/${gardenName}")
            }
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "برداشت سالیانه", style = MaterialTheme.typography.subtitle1, color = Color.Gray)

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val harvestList = listOf(15000.0, 10250.5, 11000.0, 1000.1, 16020.8, 9800.5)
                val yearList = listOf("96", "97", "98", "99", "400", "401")
                val normalized = normalizer(harvestList)
                LazyRow{
                    items(harvestList.size){ index ->
                            Bars(value = normalized[index], year = yearList[index])
                    }
                }
            }
        }
    }
}

@Composable
fun Bars(value : Float, year: String){

    val maxHeight = 130
    var clicked by remember {
        mutableStateOf(false)
    }
    
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxHeight()
        ,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height((value * maxHeight).dp)
                .background(MainGreen, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .clickable { clicked = !clicked }
        )
        Text(text = year, color = Color.Black, style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(top = 10.dp))
    }
}

fun normalizer(harvestList : List<Double>) : List<Float> {
    val normalizedList = mutableListOf<Float>()

    for (h in harvestList){
        val normalized = ((h - harvestList.minOrNull()!!)/(harvestList.maxOrNull()!! - harvestList.minOrNull()!!)).toFloat()
        normalizedList.add(normalized)
    }
    return normalizedList
}