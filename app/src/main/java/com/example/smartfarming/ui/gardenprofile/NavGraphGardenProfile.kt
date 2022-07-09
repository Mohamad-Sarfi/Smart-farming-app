package com.example.smartfarming.ui.gardenprofile

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.smartfarming.ui.addactivity.AddActivity
import com.example.smartfarming.ui.gardenprofile.composables.Weather
import com.example.smartfarming.ui.gardens.composables.GardenProfile
import com.example.smartfarming.ui.harvest.harvest_archive.GardenHarvestScreen

@Composable
fun NavGraphGardenProfile(
    navController: NavHostController,
    garden : State<Garden?>,
    viewModel: GardenProfileViewModel
){
    val context = LocalContext.current
    val irrigation = AppScreensEnum.IrrigationScreen.name
    val fertilization = AppScreensEnum.FertilizationScreen.name
    val pesticide = AppScreensEnum.PesticideScreen.name
    val otherActivity = AppScreensEnum.OtherActivitiesScreen.name
    val addActivity = AppScreensEnum.AddActivitiesScreen.name
    val weather = AppScreensEnum.GardenWeatherScreen.name
    val harvest = AppScreensEnum.GardenHarvestScreen.name

    NavHost(navController = navController,
        startDestination = ScreensEnumGardenProfile.MainScreen.name
    ) {


        composable(route = ScreensEnumGardenProfile.MainScreen.name){
            GardenProfile(garden, navController)
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
            Irrigation(gardenName = gardenName!!, navController)
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
            Fertilization(
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
            Others(gardenName = gardenName!!, navController = navController, act = "act")
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
        

    }
}