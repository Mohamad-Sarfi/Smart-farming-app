package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Article
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.MainOrange
import com.example.smartfarming.ui.addactivities.ui.theme.Purple500
import com.example.smartfarming.ui.addgarden.AddGarden
import com.example.smartfarming.ui.gardenprofile.GardenProfileActivity
import com.example.smartfarming.ui.home.HomeViewModel
import com.example.smartfarming.ui.home.HomeViewModelFactory
import com.example.smartfarming.utils.getTaskColor
import com.example.smartfarming.utils.getTaskIcon
import com.example.smartfarming.utils.getTaskList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCompose(navController: NavHostController, setShowFAB : (Boolean) -> Unit){

    val context = LocalContext.current

    val activity = LocalContext.current as Activity

    val viewModel : HomeViewModel = viewModel(factory = HomeViewModelFactory((activity.application as FarmApplication).repo))

    val gardensList by viewModel.getGardens().observeAsState()
    var tasks = listOf<Task>()
    if (!gardensList.isNullOrEmpty()){
        tasks = getTaskList(gardensList!!)
    }

    val backDropState = rememberBackdropScaffoldState( BackdropValue.Revealed)

    val composableScope = rememberCoroutineScope()

    BackdropScaffold(
        appBar = { 
                 Row(
                     Modifier
                         .fillMaxWidth()
                         .clickable {
                            composableScope.launch {
                                backDropState.reveal()
                            }
                         }
                     ,
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.Center
                 ) {
                     Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.padding(end = 10.dp))
                     Text(text = "افزودن باغ", style = MaterialTheme.typography.body2, color = Color.White)
                 }
        },
        backLayerContent = { BackdropBackLayer(activity) },
        scaffoldState = backDropState,
        frontLayerContent = {TasksRow(tasks, viewModel, backDropState, navController){setShowFAB(it)} },
        frontLayerElevation = 4.dp,
        persistentAppBar = false,
        frontLayerScrimColor = Color.Unspecified
    ) {
        SnackbarHost(hostState = it)
    }
    /*
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LightGreen3),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            //TasksColumn(tasks, gardensList)
            Card(
                Modifier
                    .padding(bottom = 30.dp)
                    .width(230.dp),
                backgroundColor = MainGreen,
                shape = RoundedCornerShape(30.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(activity, AddGarden::class.java)
                            activity.startActivity(intent)
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
                    Text(text = "افزودن باغ جدید", color = Color.White, style = MaterialTheme.typography.body1)
                }
            }
            Image(painter = painterResource(id = R.drawable.farmer2), contentDescription = null, modifier = Modifier.size(150.dp))
        }
        //FarmingArticlesPreview(articlesList = listOf())
        TasksRow(tasks, viewModel)
    }
    */
}

@Composable
fun BackdropBackLayer(activity: Activity){
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        //TasksColumn(tasks, gardensList)
        Card(
            Modifier
                .padding(bottom = 30.dp)
                .width(230.dp),
            backgroundColor = MainOrange,
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(activity, AddGarden::class.java)
                        activity.startActivity(intent)
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp))
                Text(text = "افزودن باغ جدید", color = Color.White, style = MaterialTheme.typography.body1)
            }
        }
        Image(painter = painterResource(id = R.drawable.sprout_white), contentDescription = null, modifier = Modifier
            .size(135.dp)
            .padding(15.dp))
    }
}

@Composable
fun ManageGardenPreview(gardenList : List<Garden>?, context : Context, tasks : List<Task>){

    if (gardenList.isNullOrEmpty()){
        // When there are no gardens added yet, this will be displayed
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
        // In case gardens are added
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "باغداری شما",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 10.dp),
                    color = MainGreen
                )
                Icon(
                    Icons.Default.ArrowDownward,
                    contentDescription = "",
                    tint = MainGreen
                )
            }
            LazyColumn(){
                items(gardenList){
                    com.example.smartfarming.ui.home.composables.GardenCardHome(garden = it, tasks = tasks)
                }
        }
    }
}

@Composable
fun FarmingArticlesPreview(articlesList : List<Article>?){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
        ,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = "توصیه های باغداری",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp),
                color = MainGreen
            )

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "",
                tint = MainGreen
            )
        }

        LazyRow(){
            item(){
                ArticleItem(
                    article = Article(0, "آبیاری نهال پسته", "لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است و برای شرایط فعلی تکنولوژی مورد نیاز و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد. کتابهای زیادی در شصت و سه درصد گذشته، حال و آینده شناخت فراوان جامعه و متخصصان را می طلبد تا با نرم افزارها شناخت بیشتری را برای طراحان رایانه ای علی الخصوص طراحان خلاقی و فرهنگ پیشرو در زبان فارسی ایجاد کرد. در این صورت می توان امید داشت که تمام و دشواری موجود در ارائه راهکارها و شرایط سخت تایپ به پایان رسد وزمان مورد نیاز شامل حروفچینی دستاوردهای اصلی و جوابگوی سوالات پیوسته اهل دنیای موجود طراحی اساسا مورد استفاده قرار گیرد.", "", "", "Mohamad Sarfi", listOf(), listOf()))
            }
            item(){
                ArticleItem(article = Article(0, "سم پاشی فروردین", "", "", "", "Mohamad Sarfi", listOf(), listOf()))
            }
            item(){
                ArticleItem(article = Article(0, "سم پاشی فروردین", "", "", "", "Mohamad Sarfi", listOf(), listOf()))
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasksRow(
    tasks: List<Task>,
    viewModel: HomeViewModel,
    backdropState : BackdropScaffoldState,
    navController: NavHostController,
    setShowFAB: (Boolean) -> Unit
){

    Card(
        modifier = Modifier.fillMaxHeight(1f),
        backgroundColor = LightBackground,
        elevation = 2.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "باغداری شما",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 10.dp),
                    color = MainGreen
                )
                Icon(
                    Icons.Default.ArrowDownward,
                    contentDescription = "",
                    tint = MainGreen
                )
            }

            Crossfade(
                backdropState.currentValue,
                animationSpec = tween(500)
            ) {
                when(it){
                    BackdropValue.Revealed -> RevealedFrontLayer(tasks, viewModel, navController){setShowFAB(it)}
                    BackdropValue.Concealed -> ConcealedFrontLayer(tasks, viewModel, navController){setShowFAB(it)}
                }
            }
        }
        
    }

}


@Composable
fun RevealedFrontLayer(tasks: List<Task>, viewModel: HomeViewModel, navController : NavHostController, setShowFAB: (Boolean) -> Unit){

    val activity = LocalContext.current as Activity

    Column() {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ActivityGroupSelector(
                ActivityTypesEnum.IRRIGATION.name,
                viewModel.selectedActivityGroup.value
            )
            { viewModel.setSelectedActivityGroup(it) }

            ActivityGroupSelector(
                ActivityTypesEnum.FERTILIZATION.name,
                viewModel.selectedActivityGroup.value
            )
            { viewModel.setSelectedActivityGroup(it) }

            ActivityGroupSelector(
                ActivityTypesEnum.PESTICIDE.name,
                viewModel.selectedActivityGroup.value
            )
            { viewModel.setSelectedActivityGroup(it) }

            ActivityGroupSelector(
                ActivityTypesEnum.Other.name,
                viewModel.selectedActivityGroup.value
            )
            { viewModel.setSelectedActivityGroup(it) }
        }

        LazyRow() {
            items(tasks) { item ->
                TaskCard2(item, navController){
                    val intent = Intent(activity, GardenProfileActivity::class.java)
                    intent.putExtra("gardenName", item.garden_name)
                    intent.putExtra("taskScreenShow", true)
                    activity.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun ConcealedFrontLayer(tasks: List<Task>, viewModel: HomeViewModel, navController : NavHostController, setShowFAB: (Boolean) -> Unit){
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(5.dp)
    ) {
        RevealedRow(tasks, ActivityTypesEnum.FERTILIZATION.name, viewModel, navController){setShowFAB(it)}
        RevealedRow(tasks, ActivityTypesEnum.IRRIGATION.name, viewModel, navController){setShowFAB(it)}
        RevealedRow(tasks, ActivityTypesEnum.PESTICIDE.name, viewModel, navController){setShowFAB(it)}
        RevealedRow(tasks, ActivityTypesEnum.Other.name, viewModel, navController){setShowFAB(it)}
    }
}

@Composable
fun ActivityGroupSelector(activityName : String ,
                          selectedActivity : String,
                          setSelectedActivity : (String) -> Unit
){
    Box(
        modifier = Modifier
            .padding(5.dp)
            .clip(CircleShape)
            .background(
                if (activityName == selectedActivity) getTaskColor(activityName) else Color.White
            )
            .clickable {
                setSelectedActivity(activityName)
            }
            .padding(8.dp)
    ) {
        Icon(
            getTaskIcon(activityName),
            contentDescription = null,
            modifier = Modifier.size(35.dp),
            tint = if (activityName == selectedActivity) Color.White else getTaskColor(activityName).copy(alpha = 0.7f)
        )
    }
}

@Composable
fun RevealedRow(tasks: List<Task>, activityName: String, viewModel: HomeViewModel, navController : NavHostController, setShowFAB: (Boolean) -> Unit){

    val activity = LocalContext.current as Activity

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .background(
                        Color.White
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    getTaskIcon(activityName),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = getTaskColor(activityName).copy(alpha = 0.7f)
                )
            }
            Text(text = viewModel.taskName(activityName), style = MaterialTheme.typography.body1, color = viewModel.taskColor(activityName), modifier = Modifier.padding(4.dp))

        }

        LazyRow{
            items(tasks){ item ->
                if (item.activity_type == activityName){
                    TaskCard2(task = item, navController){
                        val intent = Intent(activity, GardenProfileActivity::class.java)
                        intent.putExtra("gardenName", item.garden_name)
                        intent.putExtra("taskScreenShow", true)
                        activity.startActivity(intent)
                    }
                }
            }
        }
    }
}