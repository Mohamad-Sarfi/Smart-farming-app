package com.example.smartfarming.ui.addactivity

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModelFactory

@Composable
fun AddActivity(
    navController : NavHostController,
    viewModel: AddActivitiesViewModel
){
    val gardensList = viewModel.getGardens().observeAsState()
    val gardensNameList = arrayListOf<String>()

    if (gardensList.value != null){
        for (garden in gardensList.value!!){
            gardensNameList.add(garden.name)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Cards(
            navController,
            viewModel = viewModel,
            gardensNameList
        )
    }
}