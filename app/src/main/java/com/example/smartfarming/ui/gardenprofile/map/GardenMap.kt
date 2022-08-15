package com.example.smartfarming.ui.gardenprofile.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.home.composables.backgroundColor
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType


@Composable
fun GardenMap(gardenName : String){
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f),
                properties = MapProperties(mapType = MapType.SATELLITE)
            ){

            }

            Column(Modifier.fillMaxWidth().fillMaxHeight()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MainGreen)
                        .padding(horizontal = 25.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = MainGreen
                        ),
                        modifier = Modifier.offset(y = -43.dp).size(65.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                    Text(text = gardenName + " موقعیت باغ", color = Color.White, style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}