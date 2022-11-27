package com.example.smartfarming.ui.gardens.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Beenhere
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.authentication.ui.theme.RedFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.gardenprofile.GardenProfileViewModel
import com.example.smartfarming.ui.gardenprofile.composables.ReportDiagram
import com.example.smartfarming.ui.gardenprofile.composables.ToDos
import com.example.smartfarming.ui.home.composables.MyFAB
import com.example.smartfarming.ui.home.composables.TaskCard2
import com.example.smartfarming.utils.getTaskList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GardenProfile(garden : State<Garden?>, navController: NavHostController, viewModel: GardenProfileViewModel){
    val context = LocalContext.current
    var fabExtended by remember{
        mutableStateOf(false)
    }

    viewModel.getAllGardens()

    var tasks = listOf<Task>()

    Scaffold(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize(1f),
        floatingActionButton = {
            MyFAB(context = context, fabExtended = fabExtended) {
                fabExtended = !fabExtended
            }
        },
        backgroundColor = LightBackground
    ) {
        if (viewModel.garden.value != null){
            viewModel.getGardenTasks()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .scrollable(rememberScrollState(), Orientation.Vertical)
                    .background(LightGray2),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GardenTitle(gardenName = garden.value!!.title, navController)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())) {
                    ReportDiagram()

                    Report(navController, garden.value!!.title)

                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp), thickness = 1.dp, color = Color.Gray.copy(.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        MainIcons(Icons.Default.Thermostat, "آب و هوا", Blue700){
                            navController.navigate(route = "${AppScreensEnum.GardenWeatherScreen.name}/${garden.value!!.title}")
                        }

                        MainIcons(Icons.Outlined.Inventory, "محصولات", YellowPesticide){
                            navController.navigate("${AppScreensEnum.GardenHarvestScreen.name}/${garden.value!!.title}")
                        }

                        MainIcons(Icons.Outlined.LocationOn, "مکان نما", RedFertilizer){
                            navController.navigate("${AppScreensEnum.GardenMapScreen.name}/${garden.value!!.title}")
                        }

                    }

                    val thisGardenTask = ArrayList<Task>()
                    for (task in tasks){
                        if (task.gardenIds.contains(garden.value!!.id)){
                            thisGardenTask.add(task)
                        }
                    }

                    //List of tasks
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Divider(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp), thickness = 1.dp, color = Color.Gray.copy(.3f))
                        
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            verticalAlignment = Alignment.CenterVertically, 
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Icon(Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        navController.navigate("${AppScreensEnum.AddTaskScreen.name}/${garden.value}")
                                    }
                            )

                            Row(
                                modifier = Modifier
                                    .clickable { navController.navigate(route = AppScreensEnum.GardenTasksScreen.name) },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "یادآور فعالیت ها",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.Black,
                                    modifier = Modifier.padding(5.dp)
                                )

                                Icon(Icons.Default.Alarm, contentDescription = null, modifier = Modifier
                                    .size(40.dp)
                                    .padding(5.dp), tint = Color.Black)
                            }
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding =  PaddingValues(10.dp),
                            modifier = Modifier
                                .height(550.dp)
                                .padding(horizontal = 12.dp),
                        ){
                            items(viewModel.tasksList){ item ->
                                TaskCard2(
                                    task = item,
                                    navController = navController,
                                    setTaskStatus = {stat -> viewModel.updateTaskStatus(item.id, stat)},
                                    deleteTask = {viewModel.deleteTask(it)}) {
                                }
                            }
                        }
                    }

    //                LazyColumn(
    //                    modifier = Modifier.height(400.dp)
    //                ){
    //                    items(thisGardenTask){ task ->
    //                        ToDos(task = task, navController = navController)
    //                    }
    //                }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}



@Composable
fun Report(navController: NavHostController, gardenName: String){
    Card(
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = LightGray
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable {
                navController.navigate(route = "${AppScreensEnum.GardenReportScreen.name}/${gardenName}")
            }
            .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(id = R.drawable.bar_chart),
                contentDescription = "",
                tint = MainGreen,
                modifier = Modifier
                    .padding(8.dp)
                    .size(55.dp)
            )
            Text(
                text = "گزارش فعالیت ها",
                style = MaterialTheme.typography.body1,
                color = MainGreen
            )
        }
    }
}

@Composable
fun MainIcons(
    icon : ImageVector,
    text : String,
    color: Color ,
    clickHandler : () -> Unit
){

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clickable { clickHandler() }
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = "",
            modifier = Modifier.size(40.dp),
            tint = color
        )

        Text(text = text, style = MaterialTheme.typography.subtitle1, color = BorderGray)
    }
}

@Composable
fun GardenTitle(gardenName : String, navController: NavHostController){
    Box(
        Modifier
            .padding(bottom = 55.dp, start = 0.dp, end = 0.dp)
            .fillMaxWidth()
            .height(140.dp)
            .background(MainGreen)
            .padding(0.dp)) {
        Image(painterResource(id = R.drawable.background_pic), contentDescription = null, modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MainGreen.copy(.4f)))
        Card( modifier = Modifier
            .offset(y = 105.dp)
            .padding(horizontal = 15.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp, topEnd = 40.dp, topStart = 40.dp),
            elevation = 4.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)) {
                Icon(Icons.Default.Edit, contentDescription = "", tint = MainGreen, modifier = Modifier
                    .clickable {
                        navController.navigate(route = "${AppScreensEnum.GardenEditScreen.name}/$gardenName")
                    }
                    .padding(5.dp))
                Text(text = gardenName, style = MaterialTheme.typography.h5, color = MainGreen, modifier = Modifier.padding(5.dp))
            }
        }
    }
}

