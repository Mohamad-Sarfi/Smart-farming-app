package com.example.smartfarming.ui.gardenprofile.taskScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DisabledByDefault
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.Blue500
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Yellow500
import com.example.smartfarming.ui.gardenprofile.GardenProfileViewModel
import com.example.smartfarming.ui.home.composables.TaskCard2
import com.example.smartfarming.utils.getActivityScreen
import com.example.smartfarming.utils.getTaskList
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(viewModel: GardenProfileViewModel, navtController: NavHostController){
    viewModel.getAllGardens()
    var showCards by remember {
        mutableStateOf(false)
    }

    var showDone by remember {
        mutableStateOf(false)
    }
    var showUndone by remember {
        mutableStateOf(false)
    }
    var showPending by remember {
        mutableStateOf(false)
    }

    if (viewModel.garden.value != null){
        viewModel.getGardenTasks()
    }

    viewModel.getGardenTasks()

    LaunchedEffect(key1 = true){
        delay(100)
        showCards = true
    }

    BackHandler() {
        backButtonClickHandler(navtController, showCards){
            showCards = it
        }
    }

    Scaffold(
        backgroundColor = LightBackground,
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            backButtonClickHandler(navtController, showCards) {
                                showCards = it
                            }
                        },
                    tint = MaterialTheme.colors.onPrimary)
                Text(
                    text = "باغداری باغ " + "${viewModel.garden.value?.title}",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Crossfade(
                targetState = showCards,
                animationSpec = tween(200, 300)
            ) { flag ->
                when(flag){
                    true -> Column() {
                        TaskStatusCard(
                            title = "در انتظار انجام",
                            icon = Icons.Default.PendingActions,
                            color = Yellow500.copy(.6f),
                            showCards,
                            showDetails = {
                                showPending = it
                                showDone = false
                                showUndone = false
                            }){showCards = it}

                        TaskStatusCard(
                            title = "انجام شده", icon = Icons.Default.Done,
                            color = MainGreen.copy(.6f), showCards,
                            showDetails = {
                                showPending = false
                                showDone = it
                                showUndone = false
                            }){showCards = it}

                        TaskStatusCard(
                            title = "منقضی شده",
                            icon = Icons.Default.DisabledByDefault,
                            color = Color.Red.copy(.6f), showCards,
                            showDetails = {
                                showPending = false
                                showDone = false
                                showUndone = it
                            }
                        ){showCards = it}
                    }
                    false -> Column(
                        Modifier
                            .fillMaxSize()
                            .padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                        TasksGrid(viewModel, navtController, showDone, showUndone, showPending)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TaskStatusCard(
    title : String,
    icon : ImageVector,
    color: Color,
    showCards : Boolean,
    showDetails : (Boolean) -> Unit,
    setShowCard : (Boolean) -> Unit
) {
    Card(
        onClick = {
            setShowCard(false)
            showDetails(true)
                  },
        elevation = 0.dp,
        backgroundColor = color,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth(.65f)
                .padding(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text =title, style = MaterialTheme.typography.h6, color = Color.White)
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier
                .size(55.dp)
                .padding(8.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TasksGrid(viewModel: GardenProfileViewModel, navtController: NavHostController,showDone: Boolean, showUndone : Boolean, showPending : Boolean) {
    var title = ""

    when {
        showUndone -> {
            viewModel.setShownTaskList(TaskStatusEnum.IGNORED.name)
            title = "منقضی شده"
        }
        showDone -> {
            viewModel.setShownTaskList(TaskStatusEnum.DONE.name)
            title = "انجام شده"
        }
        showPending -> {
            viewModel.setShownTaskList(TaskStatusEnum.TODO.name)
            title = "در انتظار انجام"
        }
    }

    Column() {
        Row(
            Modifier.fillMaxWidth().padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(5.dp)
        ){
            items(viewModel.shownList){ item ->
                TaskCard2(task = item, navController = navtController, oneStepClick = true, setTaskStatus = {}, deleteTask = {viewModel.deleteTask(it)}){
                    navtController.navigate(route = "${getActivityScreen(item.activityType)}/${viewModel.garden.value?.title}")
                }
            }
        }
    }
}

private fun backButtonClickHandler(navtController: NavHostController, showCards: Boolean, setShowCard: (Boolean) -> Unit){
    if (!showCards){
        setShowCard(true)
    } else {
        navtController.popBackStack()
    }
}