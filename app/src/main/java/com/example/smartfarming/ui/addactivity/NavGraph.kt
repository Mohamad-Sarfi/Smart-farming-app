package com.example.smartfarming.ui.addactivities

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivity.activityscreens.Fertilization
import com.example.smartfarming.ui.addactivity.activityscreens.Irrigation
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.addactivity.AddActivity
import com.example.smartfarming.ui.addactivity.activityscreens.Others
import com.example.smartfarming.ui.addactivity.activityscreens.Pesticides

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: AddActivitiesViewModel
){
    // Routes
    val homeActivity = ScreensEnumActivities.HomeActivity.name
    val irrigation = AppScreensEnum.IrrigationScreen.name
    val fertilization = AppScreensEnum.FertilizationScreen.name
    val activityScreen = AppScreensEnum.OtherActivitiesScreen.name
    val pesticideScreen = AppScreensEnum.PesticideScreen.name


    NavHost(
        navController = navController,
        startDestination = ScreensEnumActivities.HomeActivity.name
    ){
            composable(
                route = ScreensEnumActivities.HomeActivity.name
            ){
                AddActivity(navController = navController, viewModel)
            }
            composable(
                route = "$irrigation/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                    }
                )
            ){ entry ->
                val gardenName = entry.arguments?.getString("name")
                //Irrigation(gardenName = gardenName!!, navController = navController)
                com.example.smartfarming.ui.addactivity.activityscreens.irrigation.Irrigation()
            }
        composable(
                route = "$fertilization/{name}",
                arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                    }
                )
            ){ entry ->
                val gardenName = entry.arguments?.getString("name")
                com.example.smartfarming.ui.addactivity.activityscreens.fertilization.Fertilization(gardenName!!)
            }

        composable(
            route = "$activityScreen/{gardenName}/{act}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                },
                navArgument("act"){
                    type = NavType.StringType
                }
            )
        ){entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            val act = entry.arguments?.getString("act")
            Others(gardenName = gardenName!!, navController = navController, act = act!!)
        }

        composable(
            route = "$pesticideScreen/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val  gardenName = entry.arguments?.getString("name")
            Pesticides(gardenName = gardenName!!, navController = navController)
        }
    }
}