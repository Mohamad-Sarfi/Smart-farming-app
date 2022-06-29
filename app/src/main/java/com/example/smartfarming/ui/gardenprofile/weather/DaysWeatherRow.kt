package com.example.smartfarming.ui.gardenprofile.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun DaysWeatherRow(){

    var selected by remember {
        mutableStateOf("شنبه")
    }

    val days = listOf("شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه", "شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه")
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
                items(days){item ->
                    if (selected == item){
                        WeatherRowItem(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 5.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(40.dp))
                                .background(Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF5BBEFF),
                                        Color(0xFFAEDEFE),
                                        Color(0xFFF279F2)
                                    )
                                ))
                                .clickable { selected = item }
                                .padding(vertical = 18.dp),
                            day = item,
                            selected
                        )
                    } else {
                        WeatherRowItem(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 5.dp)
                                .width(80.dp)
                                .clickable { selected = item }
                                .padding(vertical = 18.dp),
                            day = item,
                            selected
                        )
                    }



                }
            }
        }
    }
}

@Composable
fun WeatherRowItem(
    modifier: Modifier,
    day : String,
    selected : String
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "32", style = MaterialTheme.typography.body1, color = if (selected == day) Color.White else Color.Black)
        Icon(imageVector = Icons.Default.WbSunny, contentDescription = null, tint = YellowPesticide, modifier = Modifier.size(40.dp))
        Text(text = day, style = MaterialTheme.typography.subtitle1, color = if (selected == day) Color.White else Color.Black.copy(0.6f))
    }
}