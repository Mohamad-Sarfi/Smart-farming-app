package com.example.smartfarming.ui.harvest.compose.harvest_archive

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.common_composables.CommonTopBar
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.harvest_archive.GridItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HarvestArchiveHome(viewModel: HarvestViewModel, navController : NavHostController){

    val gardenList = viewModel.getGardens().observeAsState()

    Scaffold(
        topBar = { CommonTopBar(title = "محصولات باغ ها", icon = Icons.Outlined.Inventory)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(AppScreensEnum.AddHarvestScreen.name)},
                backgroundColor = MainGreen,

            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!gardenList.value.isNullOrEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding =  PaddingValues(10.dp),
                   ){
                    items(gardenList.value!!.size){ index ->
                        GridItem(garden = gardenList.value!![index], navController)
                    }
                }
            }
        }
    }
}

