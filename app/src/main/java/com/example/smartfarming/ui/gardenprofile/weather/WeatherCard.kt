package com.example.smartfarming.ui.gardenprofile.weather

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide

@Composable
fun WeatherCard(){
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .fillMaxWidth(),
        elevation = 5.dp


    ) {
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
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                val (text, degree, icon) = createRefs()

                Text(
                    text = "آفتابی",
                    style = MaterialTheme.typography.h5,
                    color = Color.White,
                    modifier = Modifier.constrainAs(text){
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                )
                Icon(
                    Icons.Filled.LightMode,
                    contentDescription = null,
                    tint = YellowPesticide,
                    modifier = Modifier
                        .constrainAs(icon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .padding(end = 30.dp)
                        .size(120.dp)
                )
                Text(
                    text = "26",
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
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                DetailInfo(text = "720hp", icon = Icons.Default.Compress)
                DetailInfo(text = "32%", icon = Icons.Outlined.WaterDrop)
                DetailInfo(text = "12 km/h", icon = Icons.Outlined.Air)
            }

            TemperatureBar()
            
        }
    }
}


@Composable
fun TemperatureBar(){
        Row(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(BlueWatering.copy(0.3f))
                .padding(vertical = 10.dp)

            ,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
        ) {
            DetailedTempBar(4, 32.0f, Icons.Outlined.WbSunny)
            DetailedTempBar(8, 30.0f, Icons.Outlined.WbSunny)
            DetailedTempBar(12, 28.0f, Icons.Outlined.WbSunny)
            DetailedTempBar(16, 32.0f, Icons.Outlined.WbSunny)
            DetailedTempBar(16, 32.0f, Icons.Outlined.WbSunny)
            DetailedTempBar(16, 32.0f, Icons.Outlined.WbSunny)
        }

}

@Composable
fun DetailInfo(text : String, icon: ImageVector){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
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
fun DetailedTempBar(
    hour : Int,
    temperature : Float,
    icon: ImageVector
){
    val min = 23f
    val max = 37.5f
    val bar_height = (((temperature - min)/(max - min) ) * 100).toInt()
    Log.i("temperature", "${bar_height}")
    Column(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(2.dp, BlueWatering)
                .clip(RectangleShape)
                .background(Color.White)
                .width(10.dp)
                .height(bar_height.dp)
            ,

        )
        Icon(icon, contentDescription = null, tint = YellowPesticide)
        Text(text = "${hour}:00", style = MaterialTheme.typography.subtitle2, color = Color.White)

    }
}

@Composable
@Preview
fun PreviewWeatherCard(){
    WeatherCard()
}