package com.example.smartfarming.ui.harvest.compose.harvest_archive

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.common_composables.CommonTopBar
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.harvest_archive.GridItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HarvestArchiveHome(viewModel: HarvestViewModel, navController : NavHostController){

    val gardenList = viewModel.getGardens().observeAsState()

    Scaffold(
        topBar = { CommonTopBar(title = "محصولات باغ ها", icon = Icons.Outlined.Inventory)}
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
                    cells = GridCells.Fixed(2),
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

