package com.example.smartfarming.ui.gardenprofile.weather

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.data.network.resources.weather_response.DailyItem
import com.example.smartfarming.data.network.resources.weather_response.WeatherResponse
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.utils.PersianCalender

@Composable
fun DaysWeatherRow(
    weatherResponse : WeatherResponse?,
    viewModel: WeatherViewModel,
    selected: Int,
    changeSelectedDay : (Int) -> Unit
){



    if (weatherResponse != null){
        Log.i("xxxx" , viewModel.timeConverter(weatherResponse.current.dt).substring(0, 10))
    }

    if (weatherResponse != null){
        Column(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
            , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End,

        ) {
            Text(
                text = "هفتگی",
                style = MaterialTheme.typography.h5,
                color = BlueWatering,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                LazyRow(){
                    items(weatherResponse.daily!!){item ->
                        if (selected == weatherResponse.daily.indexOf(item)){
                            WeatherRowItemExpanded(
                                day = viewModel.getDayOfMonth(weatherResponse.daily.indexOf(item)),
                                item = item,
                                viewModel = viewModel,
                                index = weatherResponse.daily.indexOf(item)
                            )
                        } else {
                            WeatherRowItem(
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 5.dp)
                                    .width(80.dp)
                                    .clickable {
                                        changeSelectedDay(
                                            weatherResponse.daily.indexOf(
                                                item
                                            )
                                        )
                                    }
                                    .padding(vertical = 15.dp),
                                day = viewModel.getDayOfMonth(weatherResponse.daily.indexOf(item)),
                                selected,
                                item
                            )
                        }



                    }
                }
            }
        }
    }


}

@Composable
fun WeatherRowItem(
    modifier: Modifier,
    day : Int,
    selected : Int,
    item : DailyItem
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${(item.temp.day - 273.15).toInt()}°", style = MaterialTheme.typography.body1, color = if (selected == day) Color.White else Color.Black)
        Icon(
            WeatherIcon(weatherDescription = item.weather!![0].description, false),
            contentDescription = null, modifier = Modifier.size(45.dp).padding(vertical = 5.dp))
        Text(text = day.toString(), style = MaterialTheme.typography.subtitle1, color = if (selected == day) Color.White else Color.Black.copy(0.6f))
    }
}

@Composable
fun WeatherRowItemExpanded(
    day : Int,
    item : DailyItem,
    viewModel: WeatherViewModel,
    index : Int
) {

    Column(
        Modifier
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .border(2.dp, BlueWatering, RoundedCornerShape(30.dp))
            .clickable { }
            .padding(vertical = 15.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${(item.temp.min - 273.15).toInt()}° / ${(item.temp.max - 273.15).toInt()}°",
            style = MaterialTheme.typography.body1,
            color = Color.Black
        )

        Image(
            WeatherIcon(weatherDescription = item.weather!![0].description),
            contentDescription =null,
            modifier = Modifier
                .size(45.dp)
                .padding(vertical = 5.dp)
        )
        Text(text =viewModel.getPersianDayOfWeek(viewModel.getPersianDayOfWeekWeather(index)) + " " + day.toString() , style = MaterialTheme.typography.subtitle1, color = Color.Black)
    }
}