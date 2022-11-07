package com.example.smartfarming.ui.gardenprofile.editGarden

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.Garden
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditMap(gardenName: String, navHostController: NavHostController){
    val viewModel : EditMapViewModel = hiltViewModel()

    viewModel.getGarden(gardenName)

    val garden by viewModel.garden

    Scaffold() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (garden != null){
                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 25.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = MaterialTheme.colors.primary)
                        Text(
                            text = "موقعیت جغرافیایی جدید باغ ${gardenName} را روی نقشه تعیین کنید. " ,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.fillMaxWidth(.8f),
                            color = Color.Black.copy(.7f)
                        )
                    }

                    GoogleMapComp(garden!!, viewModel)
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)) {
                    Button(onClick = { /*TODO*/ }, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(.3f)) {
                        Text(text = "لغو", style = MaterialTheme.typography.body1, modifier = Modifier.padding(horizontal = 2.dp))
                    }

                    Button(onClick = { /*TODO*/ }, modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(1f)) {
                        Text(text = "تایید", style = MaterialTheme.typography.body1, modifier = Modifier.padding(horizontal = 2.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun GoogleMapComp(garden: Garden, viewModel: EditMapViewModel) {
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.7f),
        cameraPositionState = CameraPositionState(
            CameraPosition.fromLatLngZoom(LatLng(garden?.location!!.latitude, garden?.location!!.longitude), 10f),
        ),
        properties = MapProperties(mapType = MapType.SATELLITE)
    )
}