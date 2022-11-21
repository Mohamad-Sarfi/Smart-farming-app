package com.example.smartfarming.ui.harvest

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.harvest.compose.AddHarvestCompose
import com.example.smartfarming.ui.harvest.compose.HarvestCompose
import com.example.smartfarming.ui.harvest.compose.harvest_archive.HarvestArchiveHome
import com.example.smartfarming.ui.harvest.harvest_archive.GardenHarvestScreen


@Composable
fun HarvestNavGraph(
    navController: NavHostController
){
    val harvesthomeScreen = AppScreensEnum.HarvestHomeScreen.name
    val addScreen = AppScreensEnum.AddHarvestScreen.name
    val archiveScreen = AppScreensEnum.ArchiveHarvestScreen.name
    val addHarvest = AppScreensEnum.AddHarvestScreen.name

    val activity = LocalContext.current as Activity
    val viewModel : HarvestViewModel = viewModel(factory = HarvestViewModelFactory((activity.application as FarmApplication).repo))

    NavHost(
        navController = navController,
        startDestination = harvesthomeScreen
    ){
        composable(route = harvesthomeScreen){
            HarvestCompose(navController = navController)
        }

        composable(route = addScreen){
            AddHarvestCompose(viewModel = viewModel, navController = navController)
        }

        composable(route = archiveScreen){
            HarvestArchiveHome(viewModel, navController)
        }

        composable(
            "${AppScreensEnum.GardenHarvestScreen.name}/{name}",
            arguments = listOf(navArgument("name"){
                type = NavType.StringType
            })
        ){ entry ->
            val gardenName = entry.arguments?.getString("name")
            GardenHarvestScreen(gardenName!!, navController)
        }

    }

}