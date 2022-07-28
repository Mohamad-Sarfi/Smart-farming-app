package com.example.smartfarming.ui.gardenprofile.weather

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.data.network.resources.weather_response.HourlyItem
import com.example.smartfarming.data.network.resources.weather_response.WeatherResponse
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.utils.PersianCalender

@Composable
fun WeatherCard(
    weatherResponse: WeatherResponse?,
    viewModel: WeatherViewModel
){
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        if (weatherResponse != null){
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF5BBEFF),
                                Color(0xFFAEDEFE),
                                Color(0xFFAEDEFE),
                                Color(0xFFF279F2)
                            )
                        )
                    )
                    .padding(vertical = 20.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherInfoCard(weatherResponse, viewModel)
                TemperatureBar(weatherResponse,viewModel)
            }
        } else {
            WeatherWaiting()
        }
    }

}


@Composable
fun WeatherInfoCard(
    weatherResponse: WeatherResponse,
    viewModel: WeatherViewModel
){
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

        val (text, degree, icon, date) = createRefs()
        Row(
            Modifier
                .fillMaxWidth()
                .constrainAs(date) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val persianCalender = PersianCalender.getShamsiDateMap()
            val month = PersianCalender.getMonthName()
            Text(
                text = month, style = MaterialTheme.typography.body1, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp))
            Text(
                text = persianCalender["day"].toString(), style = MaterialTheme.typography.body1, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp))
            Text(
                text = viewModel.getPersianDayOfWeek(), style = MaterialTheme.typography.body1, color = Color.White, modifier = Modifier.padding(horizontal = 4.dp))
        }

        Text(
            text = viewModel.getPersianWeatherDescription(weatherResponse.current.weather!![0].description),
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier
                .constrainAs(text) {
                    end.linkTo(parent.end)
                    top.linkTo(date.bottom)
                }
                .padding(end = 6.dp)
        )
        Image(
            painter = WeatherIcon(weatherDescription = weatherResponse.current.weather!![0].description),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    top.linkTo(date.bottom)
                }
                .padding(end = 30.dp, top = 10.dp, start = 10.dp)
                .size(110.dp)
        )
        Text(
            text = "${(weatherResponse.current.temp - 273.15).toInt()}°",
            style = MaterialTheme.typography.h1,
            color = Color.White,
            modifier = Modifier.constrainAs(degree){
                end.linkTo(parent.end)
                top.linkTo(text.bottom)
                start.linkTo(icon.end)
            }
        )
    }

    Row(
        Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        DetailInfo(text = "${weatherResponse.current.pressure} pa", icon = Icons.Default.Compress, "فشار هوا")
        DetailInfo(text = "${weatherResponse.current.humidity}%", icon = Icons.Outlined.WaterDrop, "رطوبت هوا")
        DetailInfo(text = "${weatherResponse.current.windSpeed} km/h", icon = Icons.Outlined.Air, "سرعت وزش باد")
    }
}

@Composable
fun TemperatureBar(
    weatherResponse: WeatherResponse,
    viewModel: WeatherViewModel
){
    Row(
        Modifier
            .padding(top = 13.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(BlueWatering.copy(0.3f))
            .padding(vertical = 10.dp, horizontal = 15.dp)

        ,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
    ) {

        LazyRow(){
            items(items = weatherResponse.hourly!!){ item ->
                HourlyTemp(
                    hour = viewModel.timeConverter(item.dt).substring(11,13).toInt() ,
                    hourlyItem = item ,
                    icon = Icons.Default.WbSunny
                )
            }
        }
        }

}

@Composable
fun DetailInfo(text : String, icon: ImageVector, title : String){

    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .clickable {
                Toast
                    .makeText(context, title, Toast.LENGTH_SHORT)
                    .show()
            }
    ) {
        Text(text = text, style = MaterialTheme.typography.subtitle1, color = Color.White)

        Icon(
            icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}

@Composable
fun HourlyTemp(
    hour : Int,
    hourlyItem: HourlyItem,
    icon: ImageVector
){
    Column(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${(hourlyItem.temp - 273.15).toInt()}°" ,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 3.dp),
            color = Color.White
        )
        Icon(WeatherIcon(weatherDescription = hourlyItem.weather!![0].description), contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
        Text(
            text = "${hour}:00",
            style = MaterialTheme.typography.subtitle2,
            color = Color.White,
            modifier = Modifier.padding(top = 3.dp),
        )

    }
}


@Composable
fun WeatherWaiting(){
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5BBEFF),
                        Color(0xFFAEDEFE),
                        Color(0xFFAEDEFE),
                        Color(0xFFF279F2)
                    )
                )
            )
            .padding(vertical = 60.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.HourglassTop,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .size(90.dp)
        )
        Text(
            text = "در حال دریافت اطلاعات آب و هوا",
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
    }
}

/*
@Composable
@Preview
fun PreviewWeatherCard(){
    WeatherCard()
}
*/