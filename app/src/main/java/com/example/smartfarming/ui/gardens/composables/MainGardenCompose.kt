package com.example.smartfarming.ui.gardens.composables

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.common_composables.CommonTopBar
import com.example.smartfarming.ui.common_composables.NoGardenAdded
import com.example.smartfarming.ui.gardens.GardensViewModel
import com.example.smartfarming.ui.gardens.GardensViewModelFactory
import com.example.smartfarming.ui.home.composables.MyFAB


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GardenCompose(){
    val viewModel : GardensViewModel = hiltViewModel()
    val gardenList by viewModel.getGardens().observeAsState()

    Scaffold(
        topBar = { CommonTopBar(title = "باغ های شما", icon = Icons.Outlined.Eco)},
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightBackground)
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.verticalScroll(rememberScrollState())
                    .weight(1f, false)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (gardenList.isNullOrEmpty()){
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NoGardenAdded()
                    }
                } else {
                    LazyColumn{
                        items(gardenList!!){
                            GardenCard(garden = it, viewModel)
                        }
                    }
                }
            }
        }
    }
}

