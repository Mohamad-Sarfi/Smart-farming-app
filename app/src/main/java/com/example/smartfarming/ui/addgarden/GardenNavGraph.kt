package com.example.smartfarming.ui.addgarden

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.ui.addactivities.ScreensEnumActivities

@Composable
fun GardenNavGraph(navController: NavHostController, viewModel: AddGardenViewModel){
    val addGarden = ScreensEnumActivities.AddGardenScreen.name
    val maps = ScreensEnumActivities.MapScreen.name

    NavHost(
        navController = navController, startDestination = addGarden
    ){
        composable(
            route = addGarden
        ){
            AddGardenCompose(navController = navController, viewModel = viewModel)
        }

        composable(
            route = maps
        ){
            MapCompose(navController = navController, viewModel = viewModel)
        }
    }
}