package com.example.smartfarming.ui.addactivities

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.addactivity.AddActivity
import com.example.smartfarming.ui.addactivity.activityscreens.Fertilization
import com.example.smartfarming.ui.addactivity.activityscreens.Irrigation
import com.example.smartfarming.ui.addactivity.activityscreens.Pesticides

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: AddActivitiesViewModel,
    startScreen : String?,
    gardenId: Int?
){
    // Routes
    val homeActivity = AppScreensEnum.AddActivityHomeScreen.name
    val irrigation = AppScreensEnum.IrrigationScreen.name
    val fertilization = AppScreensEnum.FertilizationScreen.name
    val activityScreen = AppScreensEnum.OtherActivitiesScreen.name
    val pesticideScreen = AppScreensEnum.PesticideScreen.name


    NavHost(
        navController = navController,
        startDestination = homeActivity
    ){
            composable(
                route = homeActivity
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
                Irrigation(gardenName!!, navController)
            }
        composable(
                route = "$fertilization/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ){ entry ->
                val garden_id = entry.arguments?.getInt("id")
                com.example.smartfarming.ui.addactivity.activityscreens.fertilization.Fertilization(
                    gardenId = garden_id!!,
                    navController = navController
                )
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
            com.example.smartfarming.ui.addactivity.activityscreens.others.Others(gardenName = gardenName!!, navController)
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

