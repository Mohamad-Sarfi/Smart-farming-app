package com.example.smartfarming.ui.gardenprofile.composables

import android.app.Activity
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.data.network.resources.weather_response.WeatherResponse
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.gardenprofile.weather.DaysWeatherRow
import com.example.smartfarming.ui.gardenprofile.weather.WeatherCard
import com.example.smartfarming.ui.gardenprofile.weather.WeatherViewModel
import com.example.smartfarming.ui.gardenprofile.weather.WeatherViewModelFactory

@Composable
fun Weather(gardenName: String = "شماره 1"){

    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    val weatherViewModel : WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(
            (activity.application as FarmApplication).weatherRepo,
            (activity.application as FarmApplication).repo
        )
    )

    weatherViewModel.getGarden(gardenName)

    val garden = weatherViewModel.garden.observeAsState()
    var latLong : List<String>? = listOf<String>()
    var weatherResponse : State<WeatherResponse?> = mutableStateOf(null)

    var selectedDay by remember {
        mutableStateOf(value = 0)
    }

    if (garden.value != null ) {
        latLong = garden.value?.lat_long?.split("-")
    }

    if (!latLong.isNullOrEmpty()){
        weatherViewModel.getWeather("${latLong[0].trim()}0", "${latLong[1].trim()}0")
        weatherResponse = weatherViewModel.weatherResponse.observeAsState()

        Scaffold(
            Modifier
                .fillMaxSize()
                .background(LightGray2)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                TopRow(gardenName)
                WeatherCard(weatherResponse.value, selectedDay)
                DaysWeatherRow(weatherResponse.value, viewModel = weatherViewModel, selected = selectedDay){
                    selectedDay = it
                }
            }
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
        Text(text = gardenName, style = MaterialTheme.typography.h5, color = BlueWatering)
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
