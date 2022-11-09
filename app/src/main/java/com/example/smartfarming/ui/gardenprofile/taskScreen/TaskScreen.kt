package com.example.smartfarming.ui.gardenprofile.taskScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.gardenprofile.GardenProfileViewModel
import com.example.smartfarming.ui.home.composables.TaskCard2
import com.example.smartfarming.utils.getActivityScreen
import com.example.smartfarming.utils.getTaskList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(viewModel: GardenProfileViewModel, navtController: NavHostController){

    viewModel.getAllGardens()
    val gardenList = viewModel.gardensList.collectAsState(initial = listOf())

    var tasks = listOf<Task>()

    if (gardenList.value.isNullOrEmpty()){
        tasks = listOf()
    } else {
        tasks = getTaskList(gardenList.value)
    }


    Scaffold(
        backgroundColor = LightBackground,
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 20.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                        navtController.navigate(AppScreensEnum.GardenProfileHome.name){
                            popUpTo(AppScreensEnum.GardenTasksScreen.name){
                                inclusive = true
                            }
                        }

                    } ,
                    tint = MainGreen)
                Text(
                    text = "باغداری باغ " + "${viewModel.garden.value?.title}",
                    style = MaterialTheme.typography.h4,
                    color = MainGreen,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(5.dp)
            ){
                items(tasks){
                    TaskCard2(task = it, navController = navtController, oneStepClick = true, deleteTask = {viewModel.deleteTask(it)}){
                        navtController.navigate(route = "${getActivityScreen(it.activity_type)}/${viewModel.garden.value?.title}")
                    }
                }
            }
        }
    }
}