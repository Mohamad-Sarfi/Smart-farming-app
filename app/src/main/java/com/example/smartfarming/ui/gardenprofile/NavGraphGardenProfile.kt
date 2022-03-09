package com.example.smartfarming.ui.gardenprofile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.activityscreens.Fertilization
import com.example.smartfarming.ui.addactivities.activityscreens.Irrigation
import com.example.smartfarming.ui.addactivities.activityscreens.Others
import com.example.smartfarming.ui.addactivities.activityscreens.Pesticides
import com.example.smartfarming.ui.gardens.composables.GardenProfile

@Composable
fun NavGraphGardenProfile(
    navController: NavHostController,
    garden : State<Garden?>,
    viewModel: GardenProfileViewModel
){
    NavHost(navController = navController,
        startDestination = ScreensEnumGardenProfile.MainScreen.name
    ) {
        composable(route = ScreensEnumGardenProfile.MainScreen.name){
            GardenProfile(garden, navController)
        }
        composable(
            route = "${ScreensEnumGardenProfile.IrrigationScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Irrigation(gardenName = gardenName!!, navController)
        }
        composable(
            route = "${ScreensEnumGardenProfile.FertilizationScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Fertilization(
                gardenName = gardenName!!,
                navController = navController
            )
        }
        composable(
            route = "${ScreensEnumGardenProfile.PesticideScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Pesticides(gardenName = gardenName!!, viewModel = viewModel)
        }
        composable(
            route = "${ScreensEnumGardenProfile.OtherScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Others(gardenName = gardenName!!, viewModel = viewModel)
        }

    }
}