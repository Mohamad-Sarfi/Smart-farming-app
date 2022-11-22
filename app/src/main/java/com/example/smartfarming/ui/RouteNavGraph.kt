package com.example.smartfarming.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartfarming.ui.addactivity.activityscreens.fertilization.Fertilization
import com.example.smartfarming.ui.addactivity.activityscreens.irrigation.Irrigation
import com.example.smartfarming.ui.addactivity.activityscreens.others.Others
import com.example.smartfarming.ui.addactivity.activityscreens.pesticide.Pesticide
import com.example.smartfarming.ui.main_screen.MainScreen

@Composable
fun RouteNavGraph(navHostController: NavHostController) {
    val mainHome = AppScreensEnum.MainHomeScreen.name
    val irrigation = AppScreensEnum.IrrigationScreen.name
    val fertilization = AppScreensEnum.FertilizationScreen.name
    val pesticide = AppScreensEnum.PesticideScreen.name
    val other = AppScreensEnum.OtherActivitiesScreen.name

    NavHost(navController = navHostController, startDestination = mainHome){

        composable(
            route = mainHome
        ){
            MainScreen(mainNavHostController = navHostController)
        }

        composable(
            route = "$pesticide/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val  gardenName = entry.arguments?.getString("name")
            Pesticide(gardenName!!, navHostController)
        }

        composable(
            route = "$irrigation/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val  gardenName = entry.arguments?.getString("name")
            Irrigation(gardenName!!, navHostController)
        }

        composable(
            route = "$fertilization/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val  gardenName = entry.arguments?.getString("name")
            Fertilization(gardenName!!, navHostController)
        }

        composable(
            route = "$other/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val  gardenName = entry.arguments?.getString("name")
            Others(gardenName!!, navHostController)
        }
    }
}

















