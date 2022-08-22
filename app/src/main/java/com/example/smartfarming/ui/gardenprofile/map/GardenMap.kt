package com.example.smartfarming.ui.gardenprofile.map

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.home.composables.backgroundColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*


@Composable
fun GardenMap(gardenName : String){

    val activity = LocalContext.current as Activity
    val viewModel : GardenMapViewModel = viewModel(
        factory = GardenMapViewModelFactory((activity.application as FarmApplication).repo)
    )

    viewModel.getGarden(gardenName)
    val garden = viewModel.garden.observeAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (garden.value != null){
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val p0 : LatLng = LatLng(garden.value!!.polygon_list!![0].latitude,garden.value!!.polygon_list!![0].longitude)
                val p1 : LatLng = LatLng(garden.value!!.polygon_list!!.last().latitude,garden.value!!.polygon_list!!.last().longitude)

                val polygon = mutableListOf<LatLng>()

                for (p in garden.value!!.polygon_list!!){
                    polygon.add(LatLng(p.latitude, p.longitude))
                }

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.8f),
                    properties = MapProperties(
                        mapType = MapType.SATELLITE,
                    ),
                    uiSettings = MapUiSettings(),
                    cameraPositionState = CameraPositionState(position = CameraPosition.fromLatLngZoom(p0, 17f))
                ){
                    Polygon(points = polygon, fillColor = MainGreen.copy(.5f), strokeColor = Color.Red)
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()) {
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
                            modifier = Modifier
                                .offset(y = -43.dp)
                                .size(65.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                        Text(text = "موقعیت باغ " + gardenName, color = Color.White, style = MaterialTheme.typography.body1)
                    }
                    Row(
                        Modifier.fillMaxWidth().padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        Text(text = "متر مربع", style = MaterialTheme.typography.body1, color = MainGreen, modifier = Modifier.padding(3.dp))
                        Text(text = garden.value!!.area.toString(), style = MaterialTheme.typography.body2, color = MainGreen)
                        Text(text = "مساحت باغ:", style = MaterialTheme.typography.body1, color = MainGreen, modifier = Modifier.padding(3.dp))
                    }
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}