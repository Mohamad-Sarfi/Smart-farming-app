package com.example.smartfarming.ui.gardenprofile.composables.activities

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.gardenprofile.GardenProfileViewModel

@Composable
fun Fertilization(gardenName: String, viewModel: GardenProfileViewModel, navController: NavHostController){
    val garden = viewModel.getGarden(gardenName).observeAsState()
    var fertilizationDate = remember {
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))
    }
    var fertilizationType by remember {
        mutableStateOf("")
    }


}