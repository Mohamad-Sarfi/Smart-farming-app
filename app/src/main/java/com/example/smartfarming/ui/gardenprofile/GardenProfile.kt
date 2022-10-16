package com.example.smartfarming.ui.gardens.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.smartfarming.utils.getTaskList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GardenProfile(garden : State<Garden?>, navController: NavHostController, viewModel: GardenProfileViewModel){
    val context = LocalContext.current
    var fabExtended by remember{
        mutableStateOf(false)
    }

    viewModel.getAllGardens()
    val gardenList = viewModel.gardensList.collectAsState(initial = listOf())

    var tasks = listOf<Task>()

    if (gardenList.value.isNullOrEmpty()){
        tasks = listOf<Task>(
            Task(0,
                "ولک پاشی",
                activity_type = ActivityTypesEnum.FERTILIZATION.name,
                description = "به دلیل عدم تامین نیاز سرمایی",
                start_date = "",
                finish_date = "",
                garden_name = "محمد",
                recommendations = "روغن ولک",
                user_id = 5,
                seen = false
            ),
            Task(0,
                "سم پاشی",
                activity_type = ActivityTypesEnum.PESTICIDE.name,
                description = "مبارزه با پسیل",
                start_date = "",
                finish_date = "",
                garden_name = "محمد",
                recommendations = "روغن ولک",
                user_id = 5,
                seen = false
            )
            ,
            Task(0,
                "آبیاری اسفند",
                activity_type = ActivityTypesEnum.IRRIGATION.name,
                description = "موعد آبیاری اسفند",
                start_date = "",
                finish_date = "",
                garden_name = "محمد",
                recommendations = "",
                user_id = 5,
                seen = false
            )
            ,
            Task(0,
                "کود دامی",
                activity_type = ActivityTypesEnum.FERTILIZATION.name,
                description = "با توجه به ماده عالی خاک نیاز به تامین کود دامی",
                start_date = "",
                finish_date = "",
                garden_name = "اکبری",
                recommendations = "کود گاو",
                user_id = 5,
                seen = false
            )
        )
    } else {
        tasks = getTaskList(gardenList.value)
    }


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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .background(LightBackground),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GardenTitle(gardenName = garden.value!!.name, navController)
            ReportDiagram()
            Report(navController, garden.value!!.name)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                MainIcons(Icons.Default.Thermostat, "آب و هوا", Blue700){
                    navController.navigate(route = "${AppScreensEnum.GardenWeatherScreen.name}/${garden.value!!.name}")
                }
                MainIcons(Icons.Outlined.Inventory, "محصولات", YellowPesticide){
                    navController.navigate("${AppScreensEnum.GardenHarvestScreen.name}/${garden.value!!.name}")
                }
                MainIcons(Icons.Outlined.LocationOn, "مکان نما", RedFertilizer){
                    navController.navigate("${AppScreensEnum.GardenMapScreen.name}/${garden.value!!.name}")
                }

            }

            val thisGardenTask = ArrayList<Task>()
            for (task in tasks){
                if (task.garden_name == garden.value!!.name){
                    thisGardenTask.add(task)
                }
            }

            LazyColumn{
                items(thisGardenTask){ task ->
                    ToDos(task = task, navController = navController)
                }
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
            .padding(18.dp)
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
            .padding(bottom = 45.dp, start = 0.dp, end = 0.dp)
            .fillMaxWidth()
            .background(MainGreen)
            .padding(10.dp)) {
        Card( modifier = Modifier
            .offset(y = 40.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp, topEnd = 40.dp, topStart = 40.dp),
            elevation = 3.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)) {
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

