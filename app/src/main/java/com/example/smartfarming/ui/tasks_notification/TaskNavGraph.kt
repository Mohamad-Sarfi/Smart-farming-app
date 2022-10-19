package com.example.smartfarming.ui.tasks_notification

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.tasks_notification.addtask.AddTask

@Composable
fun TaskNavGraph(navHostController: NavHostController) {
    val addTask = AppScreensEnum.AddTaskScreen.name
    val tasksHome = AppScreensEnum.TasksHome.name

    NavHost(navController = navHostController, startDestination = addTask){
        composable(
            route = tasksHome
        ){
            TasksNotificationCompose()
        }

        composable(
            route = addTask
        ){
            AddTask(navHostController)
        }
    }
}