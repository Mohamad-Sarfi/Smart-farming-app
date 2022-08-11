package com.example.smartfarming.ui.gardenprofile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivity.activityscreens.Fertilization
import com.example.smartfarming.ui.addactivity.activityscreens.Irrigation
import com.example.smartfarming.ui.addactivity.activityscreens.Others
import com.example.smartfarming.ui.addactivity.activityscreens.Pesticides
import com.example.smartfarming.ui.gardenprofile.composables.Weather
import com.example.smartfarming.ui.gardenprofile.editGarden.EditScreen
import com.example.smartfarming.ui.gardenprofile.report.Report
import com.example.smartfarming.ui.gardenprofile.taskScreen.TaskScreen
import com.example.smartfarming.ui.gardens.composables.GardenProfile
import com.example.smartfarming.ui.harvest.harvest_archive.GardenHarvestScreen

@Composable
fun NavGraphGardenProfile(
    navController: NavHostController,
    garden : State<Garden?>,
    viewModel: GardenProfileViewModel,
    taskScreen : Boolean = false
){
    val context = LocalContext.current
    val irrigation = AppScreensEnum.IrrigationScreen.name
    val fertilization = AppScreensEnum.FertilizationScreen.name
    val pesticide = AppScreensEnum.PesticideScreen.name
    val otherActivity = AppScreensEnum.OtherActivitiesScreen.name
    val addActivity = AppScreensEnum.AddActivitiesScreen.name
    val weather = AppScreensEnum.GardenWeatherScreen.name
    val harvest = AppScreensEnum.GardenHarvestScreen.name
    val report = AppScreensEnum.GardenReportScreen.name
    val home = AppScreensEnum.GardenProfileHome.name

    NavHost(navController = navController,
        startDestination = if (!taskScreen) home else AppScreensEnum.GardenTasksScreen.name
    ) {


        composable(route = home){
            GardenProfile(garden, navController, viewModel)
        }
        
        composable(
            route = "${irrigation}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            com.example.smartfarming.ui.addactivity.activityscreens.irrigation.Irrigation(
                gardenName!!,
                navHostController = navController
            )
        }
        composable(
            route = "${fertilization}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            com.example.smartfarming.ui.addactivity.activityscreens.fertilization.Fertilization(
                gardenName = gardenName!!,
                navController = navController
            )
        }


        composable(
            route = "${AppScreensEnum.PesticideScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Pesticides(gardenName = gardenName!!, navController = navController)
        }


        composable(
            route = "${otherActivity}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            com.example.smartfarming.ui.addactivity.activityscreens.others.Others(
                gardenName = gardenName!!,
                navHostController = navController
            )
        }

        composable(
            route = "${addActivity}"
        ){
            //AddActivity(navController = navController)
        }
        
        composable(
            route = "${weather}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardeName = entry.arguments?.getString("gardenName")
            Weather(gardenName = gardeName!!)
        }

        composable(
            route = "${harvest}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            GardenHarvestScreen(gardenName = gardenName!!)
        }

        composable(
            route = "${report}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Report(navController, gardenName!!)
        }

        composable(route = AppScreensEnum.GardenTasksScreen.name){
            TaskScreen(viewModel, navController)
        }

        composable(
            route = "${AppScreensEnum.GardenEditScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
                val gardenName = entry.arguments?.getString("gardenName")
                EditScreen(gardenName!!)
        }
        

    }
}