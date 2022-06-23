package com.example.smartfarming.ui.harvest

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.harvest.compose.AddHarvestCompose
import com.example.smartfarming.ui.harvest.compose.HarvestCompose
import com.example.smartfarming.ui.harvest.compose.harvest_archive.HarvestArchiveHome


@Composable
fun HarvestNavGraph(
    navController: NavHostController
){
    val homeScreen = AppScreensEnum.HarvestHomeScreen.name
    val addScreen = AppScreensEnum.AddHarvestScreen.name
    val archiveScreen = AppScreensEnum.ArchiveHarvestScreen.name

    val activity = LocalContext.current as Activity
    val viewModel : HarvestViewModel = viewModel(factory = HarvestViewModelFactory((activity.application as FarmApplication).repo))

    NavHost(
        navController = navController,
        startDestination = homeScreen
    ){
        composable(route = homeScreen){
            HarvestCompose(navController = navController)
        }

        composable(route = addScreen){
            AddHarvestCompose(viewModel = viewModel, navController = navController)
        }

        composable(route = archiveScreen){
            HarvestArchiveHome()
        }

    }

}