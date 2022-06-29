package com.example.smartfarming.ui.gardenprofile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.gardenprofile.weather.WeatherCard

@Composable
fun Weather(gardenName: String = "شماره 1"){
    Scaffold(
        Modifier
            .fillMaxSize()
            .background(LightGray2)
    ) {
        Column(Modifier.fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            TopRow(gardenName)
            WeatherCard()
        }
    }
}

@Composable
fun TopRow(gardenName: String){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = gardenName, style = MaterialTheme.typography.h4, color = BlueWatering)
        Icon(
            Icons.Outlined.PinDrop,
            contentDescription = null,
            tint = BlueWatering,
            modifier = Modifier
                .padding(5.dp)
                .size(45.dp)
        )
    }
}

@Composable
@Preview
fun PreviewWeather(){
    Weather()
}