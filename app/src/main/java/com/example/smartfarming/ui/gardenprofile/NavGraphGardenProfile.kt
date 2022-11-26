package com.example.smartfarming.ui.gardenprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivity.activityscreens.pesticide.Pesticide
import com.example.smartfarming.ui.gardenprofile.composables.Weather
import com.example.smartfarming.ui.gardenprofile.editGarden.EditMap
import com.example.smartfarming.ui.gardenprofile.editGarden.EditScreen
import com.example.smartfarming.ui.gardenprofile.map.GardenMap
import com.example.smartfarming.ui.gardenprofile.report.Report
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.IrrigationReportScreen
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.workers.WorkerUsedScreen
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.fertilizationreport.FertilizationReportScreen
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.otheractivitiesreport.OtherActivitiesReportScreen
import com.example.smartfarming.ui.gardenprofile.report.reportscreens.pesticidereport.PesticideReportScreen
import com.example.smartfarming.ui.gardenprofile.taskScreen.TaskScreen
import com.example.smartfarming.ui.gardens.composables.GardenProfile
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.compose.AddHarvestCompose
import com.example.smartfarming.ui.harvest.harvest_archive.GardenHarvestScreen
import com.example.smartfarming.ui.tasks_notification.addtask.AddTask

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraphGardenProfile(
    navController: NavHostController,
    garden : State<Garden?>,
    viewModel: GardenProfileViewModel,
    taskScreen : Boolean = false
){
    val irrigation = AppScreensEnum.IrrigationScreen.name
    val fertilization = AppScreensEnum.FertilizationScreen.name
    val pesticide = AppScreensEnum.PesticideScreen.name
    val otherActivity = AppScreensEnum.OtherActivitiesScreen.name
    val addActivity = AppScreensEnum.AddActivitiesScreen.name
    val weather = AppScreensEnum.GardenWeatherScreen.name
    val harvest = AppScreensEnum.GardenHarvestScreen.name
    val report = AppScreensEnum.GardenReportScreen.name
    val home = AppScreensEnum.GardenProfileHome.name
    val irrigationReport = AppScreensEnum.IrrigationReportScreen.name
    val fertilizationReport = AppScreensEnum.FertilizationReportScreen.name
    val pesticideReport = AppScreensEnum.PesticideReportScreen.name
    val othersReport = AppScreensEnum.OthersReportScreen.name
    val editMap = AppScreensEnum.GardenMapEditScreen.name
    val addTask = AppScreensEnum.AddTaskScreen.name
    val workersUsed = AppScreensEnum.WorkersUsedScreen.name
    val addHarvest = AppScreensEnum.AddHarvestScreen.name
    val gardenTasks = AppScreensEnum.AllGardenTasks.name

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


//        composable(
//            route = "${AppScreensEnum.PesticideScreen.name}/{gardenName}",
//            arguments = listOf(
//                navArgument("gardenName"){
//                    type = NavType.StringType
//                }
//            )
//        ){ entry ->
//            val gardenName = entry.arguments?.getString("gardenName")
//            Pesticides(gardenName = gardenName!!, navController = navController)
//        }


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
            route = addActivity
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
            GardenHarvestScreen(gardenName = gardenName!!, navController)
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
                EditScreen(gardenName!!, navController)
        }

        composable(
            route = "${AppScreensEnum.GardenMapScreen.name}/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            GardenMap(gardenName!!, navController)
        }

        //Report Screens
        composable(
            route = "$irrigationReport/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            IrrigationReportScreen(gardenName!!, navController)
        }
        
        composable(
            route = "$fertilizationReport/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            FertilizationReportScreen(gardenName = gardenName!!, navController)
        }

        composable(
            route = "$pesticideReport/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            PesticideReportScreen(gardenName!!, navController)
        }

        composable(
            route = "$pesticide/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            Pesticide(gardenName!!, navController)
        }

        composable(
            route = "$othersReport/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            OtherActivitiesReportScreen(gardenName!!, navController)
        }
        
        composable(
            route = "$editMap/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            EditMap(gardenName = gardenName!!, navController)
        }

        composable(
            route = "$addTask/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            AddTask(navController)
        }

        composable(
            route = "$workersUsed/{gardenName}",
            arguments = listOf(
                navArgument("gardenName"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            val gardenName = entry.arguments?.getString("gardenName")
            WorkerUsedScreen(gardenName!!, navController)
        }

        composable(
            route = addHarvest
        ){
            val harvestViewModel : HarvestViewModel = hiltViewModel()
            AddHarvestCompose(harvestViewModel, navController)
        }

        composable(
            route = gardenTasks,
        ) { _ ->
            TaskScreen(viewModel = viewModel, navController)
        }
    }
}