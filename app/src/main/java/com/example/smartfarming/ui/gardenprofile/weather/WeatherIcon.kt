package com.example.smartfarming.ui.gardenprofile.weather

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Storm
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.smartfarming.R

@Composable
fun WeatherIcon(weatherDescription : String, coloredIcon : Boolean = true) : Painter{
    if (coloredIcon){
        return when (weatherDescription) {
            "clear sky" -> painterResource(id = R.drawable.sun)
            "few clouds" -> painterResource(id = R.drawable.partial_cloudy)
            "scattered clouds" -> painterResource(id = R.drawable.cloud)
            "broken clouds" -> painterResource(id = R.drawable.broken_cloudy)
            "shower rain" -> painterResource(id = R.drawable.rain_shower)
            "rain" -> painterResource(id = R.drawable.rain)
            "thunderstorm" -> painterResource(id = R.drawable.thunder)
            "snow" -> painterResource(id = R.drawable.snowy)
            "mist" -> painterResource(id = R.drawable.windy)
            else -> painterResource(id = R.drawable.sun)
        }
    } else {
        return when (weatherDescription) {
            "clear sky" -> painterResource(id = R.drawable.sun_line)
            "few clouds" -> painterResource(id = R.drawable.partial_cloudy_line)
            "scattered clouds" -> painterResource(id = R.drawable.cloud_line)
            "broken clouds" -> painterResource(id = R.drawable.broken_cloudy_line)
            "shower rain" -> painterResource(id = R.drawable.rain_shower_line)
            "rain" -> painterResource(id = R.drawable.rain_line)
            "thunderstorm" -> painterResource(id = R.drawable.thunder_line)
            "snow" -> painterResource(id = R.drawable.snowy_line)
            "mist" -> painterResource(id = R.drawable.windy_line)
            else -> painterResource(id = R.drawable.sun_line)
        }
    }

    
}
