package com.example.smartfarming.ui.home.composables

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addgarden.AddGarden
import com.example.smartfarming.ui.home.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeCompose(viewModel : HomeViewModel){

    val context = LocalContext.current

    val gardensList by viewModel.getGardens().observeAsState()

    // FAB
    var fabExtended by remember {
        mutableStateOf(false)
    }

    val tasks = listOf<Task>(
        Task(0,
            "ولک پاشی",
            activity_type = ActivityTypesEnum.FERTILIZATION.name,
            description = "به دلیل عدم تامین نیاز سرمایی",
            start_date = "",
            finish_date = "",
            garden_name = "محمد",
            recommendations = "روغن ولک",
            user_id = 5
        ),
        Task(0,
            "سم پاشی",
            activity_type = ActivityTypesEnum.PESTICIDE.name,
            description = "مبارزه با پسیل",
            start_date = "",
            finish_date = "",
            garden_name = "محمد",
            recommendations = "روغن ولک",
            user_id = 5
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
            user_id = 5
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
            user_id = 5
        )
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            MyFAB(context = context, fabExtended = fabExtended) {
                fabExtended =! fabExtended
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .height(250.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End

            ){
                ManageGardenPreview(gardensList, context, tasks)
            }
        }
    }
}

@Composable
fun ManageGardenPreview(gardenList : List<Garden>?, context : Context, tasks : List<Task>){
    if (gardenList.isNullOrEmpty()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, AddGarden::class.java)
                    context.startActivity(intent)
                }
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sprout),
                contentDescription = "",
                modifier = Modifier
                    .padding(10.dp)
                    .size(110.dp)
            )

            Text(
                text = "اولین باغ خود را اضافه کنید",
                style = MaterialTheme.typography.body2,
                color = MainGreen
            )
        }
    } else {
            Text(
                text = "باغداری شما",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(vertical = 3.dp, horizontal = 10.dp),
                color = MainGreen
            )
            LazyColumn(){
                items(gardenList){
                    com.example.smartfarming.ui.home.composables.GardenCardHome(garden = it, tasks = tasks)
                }
        }
    }
}

