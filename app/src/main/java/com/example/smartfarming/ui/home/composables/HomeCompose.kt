package com.example.smartfarming.ui.home.composables

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Article
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivity.AddActivityActivity
import com.example.smartfarming.ui.addgarden.AddGarden
import com.example.smartfarming.ui.home.HomeViewModel
import com.example.smartfarming.ui.tasks_notification.TasksNotificationActivity
import com.example.smartfarming.utils.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeCompose(navController: NavHostController, mainNavController: NavHostController,setShowFAB : (Boolean) -> Unit){

    val activity = LocalContext.current as Activity
    val viewModel : HomeViewModel = hiltViewModel()
    val gardensList by viewModel.gardensList.observeAsState()
    val backDropState = rememberBackdropScaffoldState( BackdropValue.Revealed)
    val composableScope = rememberCoroutineScope()


//    if (isNetworkStatePermissionGranted(activity) || isWriteSettingPermissionGranted(activity)){
//        val connectivityManager = activity.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
//        connectivityManager.requestNetwork(networkRequest, networkCallBack)
//    } else {
////        ActivityCompat.requestPermissions(
////            activity,
////            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
////            REQUEST_
////        )
//        Toast.makeText(activity, "not granted", Toast.LENGTH_SHORT).show()
//    }


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
        frontLayerContent = {
            TasksRow(
                viewModel,
                backDropState,
                mainNavHostController = mainNavController,
                navController,
                gardensList = gardensList,
                setBackdropState = {
                    composableScope.launch {
                        backDropState.reveal()
                    }
                }){setShowFAB(it)}
                            },
        frontLayerElevation = 4.dp,
        persistentAppBar = false,
        frontLayerScrimColor = Color.Unspecified
    ) {
        SnackbarHost(hostState = it)
    }
}

@Composable
fun BackdropBackLayer(activity: Activity){

    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .height(230.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.background_pic),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 5.dp)
                .blur(2.dp)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.primary.copy(.6f),
                            MaterialTheme.colors.primary.copy(.3f)
                        )
                    )
                ),
        )

        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                Modifier
                    .padding(bottom = 30.dp)
                    .height(50.dp)
                    .width(230.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.secondary)
                    .blur(20.dp))

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
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )

                    Text(
                        text = "افزودن باغ جدید",
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
//            Image(
//                painter = painterResource(id = R.drawable.sprout_white),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(135.dp)
//                    .padding(15.dp)
//            )
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
    viewModel: HomeViewModel,
    backdropState : BackdropScaffoldState,
    mainNavHostController: NavHostController,
    navController: NavHostController,
    gardensList: List<Garden>?,
    setBackdropState: (BackdropValue) -> Unit,
    setShowFAB: (Boolean) -> Unit
){
    val activity = LocalContext.current as Activity
    val tasks = viewModel.tasksList
    Log.i("TAG tasks list", "$tasks")

    Card(
        modifier = Modifier.fillMaxHeight(1f),
        backgroundColor = MaterialTheme.colors.surface,
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
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(shape = CircleShape,
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {
                            val intent = Intent(activity, TasksNotificationActivity::class.java)
                            activity.startActivity(intent)
                        },
                    color = LightGray2,
                    border = BorderStroke(2.dp, MainGreen)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = MainGreen, modifier = Modifier.padding(5.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "باغداری شما",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .padding(vertical = 15.dp, horizontal = 10.dp),
                        color = MainGreen
                    )
                    Icon(
                        if (backdropState.isRevealed) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = "",
                        tint = MainGreen
                    )
                }
            }

            var detailsClicked by remember {
                mutableStateOf(false)
            }
            var selectedtask by remember {
                mutableStateOf<Task?>(null)
            }

            Crossfade(
                backdropState.currentValue,
                animationSpec = tween(500)
            ) {
                when(it){
                    BackdropValue.Revealed ->
                        RevealedFrontLayer(
                            tasks,
                            viewModel,
                            navController,
                            mainNavHostController = mainNavHostController,
                            gardensList = gardensList,
                            selectedtask,
                            detailsClicked = detailsClicked,
                            setSelectedTask = {selectedtask = it}, setDetailsClicked = {detailsClicked = it}){setShowFAB(it)}

                    BackdropValue.Concealed ->
                        ConcealedFrontLayer(
                            tasks,
                            viewModel,
                            selectedtask,
                            navController,
                            setBackdropState = {state -> setBackdropState(state)},
                            setDetailsClicked = {detailsClicked = it},
                            setSelectedTask = {selectedtask = it}
                        ){setShowFAB(it)}
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RevealedFrontLayer(
    tasks: List<Task>,
    viewModel: HomeViewModel,
    navController : NavHostController,
    mainNavHostController: NavHostController,
    gardensList : List<Garden>?,
    selectedTask : Task?,
    setDetailsClicked: (Boolean) -> Unit,
    detailsClicked : Boolean,
    setSelectedTask: (Task) -> Unit,
    setShowFAB: (Boolean) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                ActivityGroupSelector(
                    ACTIVITY_LIST[0],
                    ActivityTypesEnum.IRRIGATION.name,
                    viewModel.selectedActivityGroup.value
                )
                { viewModel.setSelectedActivityGroup(it) }

                ActivityGroupSelector(
                    ACTIVITY_LIST[2],
                    ActivityTypesEnum.FERTILIZATION.name,
                    viewModel.selectedActivityGroup.value
                )
                { viewModel.setSelectedActivityGroup(it) }

                ActivityGroupSelector(
                    ACTIVITY_LIST[1],
                    ActivityTypesEnum.PESTICIDE.name,
                    viewModel.selectedActivityGroup.value
                )
                { viewModel.setSelectedActivityGroup(it) }

                ActivityGroupSelector(
                    ACTIVITY_LIST[3],
                    ActivityTypesEnum.Other.name,
                    viewModel.selectedActivityGroup.value
                )
                { viewModel.setSelectedActivityGroup(it) }
            }
        }

        if (gardensList.isNullOrEmpty()){
            Column(
                Modifier
                    .size(230.dp)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "هیچ باغی اضافه نشده!", style = MaterialTheme.typography.body1)
                val tractor_animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.tractor_animation))
                LottieAnimation(composition = tractor_animation, iterations = LottieConstants.IterateForever)
            }
        }
        LazyRow() {
            items(tasks) { item ->
                if (viewModel.selectedActivityGroup.value == "all") {
                    TaskCard2(
                        item,
                        navController,
                        setTaskStatus = {stat -> viewModel.setTaskStatus(item.id, status = stat)},
                        deleteTask = {viewModel.deleteTask(it)}) {
//                        val intent = Intent(activity, GardenProfileActivity::class.java)
//                        intent.putExtra("gardenName", "")
//                        intent.putExtra("taskScreenShow", true)
//                        activity.startActivity(intent)
                        setSelectedTask(item)
                        setDetailsClicked(true)
                    }
                } else if (viewModel.selectedActivityGroup.value == item.activityType) {
                    TaskCard2(
                        item,
                        navController,
                        setTaskStatus = {stat -> viewModel.setTaskStatus(item.id, status = stat)},
                        deleteTask = {viewModel.deleteTask(it)}) {
//                        val intent = Intent(activity, GardenProfileActivity::class.java)
//                        intent.putExtra("gardenName", "")
//                        intent.putExtra("taskScreenShow", true)
//                        activity.startActivity(intent)
                        setSelectedTask(item)
                        setDetailsClicked(true)
                    }
                }
            }
        }

        if (detailsClicked){
            TaskDetailDialog(viewModel, mainNavHostController,detailsClicked, selectedTask!!){setDetailsClicked(it)}
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskDetailDialog(viewModel: HomeViewModel, navController: NavHostController,  detailsClicked : Boolean, task: Task, setDetailsClicked : (Boolean) -> Unit) {
    Dialog(onDismissRequest = {setDetailsClicked(!detailsClicked)}) {
        Card(
            elevation = 4.dp,
            shape = MaterialTheme.shapes.large,
            backgroundColor = Color.White
        ) {
            Column(
                Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(.75f)
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (task.status == TaskStatusEnum.IGNORED.name){
                    Row(Modifier.fillMaxWidth().clip(MaterialTheme.shapes.small).background(MaterialTheme.colors.error.copy(.8f)), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(text = "مهلت انجام این یادآور تمام شده است.", style = MaterialTheme.typography.subtitle2, color = MaterialTheme.colors.onError)
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Yellow500, modifier = Modifier.padding(4.dp))
                    }
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.8f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(getTaskIcon(task.activityType), contentDescription = null, tint = getTaskColor(task.activityType), modifier = Modifier.size(80.dp))
                    Text(text = getTaskName(task.activityType), style = MaterialTheme.typography.h6)
                    RemainingDays(task = task)
                    Text(text = task.description, style = MaterialTheme.typography.body2)
                }

                Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    OutlinedButton(
                        onClick = { setDetailsClicked(!detailsClicked) },
                        modifier = Modifier.fillMaxWidth(.35f),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = MaterialTheme.colors.onPrimary,
                            contentColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(text = "لغو", style = MaterialTheme.typography.body1)
                    }

                    Button(
                        onClick = {
                            viewModel.setTaskStatus(task.id, TaskStatusEnum.DONE.name)
                            navController.navigate(route = "${getActivityScreen(task.activityType)}/${viewModel.gardensList.value!![0].title}")
                            //navController.navigate(route = "${getActivityScreen(task.activityType)}/${3}/${2}")
                                  },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(start = 4.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "انجام", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}

private fun getTaskScreen(activityType : String) : String {
    return when(activityType){
        ActivityTypesEnum.IRRIGATION.name -> AppScreensEnum.IrrigationScreen.name
        ActivityTypesEnum.FERTILIZATION.name -> AppScreensEnum.FertilizationScreen.name
        ActivityTypesEnum.PESTICIDE.name -> AppScreensEnum.PesticideScreen.name
        ActivityTypesEnum.Other.name -> AppScreensEnum.OtherActivitiesScreen.name
        else -> AppScreensEnum.OtherActivitiesScreen.name
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConcealedFrontLayer(
    tasks: List<Task>,
    viewModel: HomeViewModel,
    selectedtask: Task?,
    navController: NavHostController,
    setBackdropState : (BackdropValue) -> Unit,
    setSelectedTask: (Task) -> Unit,
    setDetailsClicked: (Boolean) -> Unit,
    setShowFAB: (Boolean) -> Unit
) {
    if (tasks.isNullOrEmpty()){
        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator()
        }
    } else {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(5.dp)
        ) {
            RevealedRow(
                tasks,
                ActivityTypesEnum.FERTILIZATION.name,
                viewModel,
                selectedtask = selectedtask,
                navController,
                setBackdropState = {setBackdropState(it)},
                setSelectedTask = {setSelectedTask(it)},
                setDetailsClicked = {setDetailsClicked(it)}
            ) { setShowFAB(it) }

            RevealedRow(
                tasks,
                ActivityTypesEnum.IRRIGATION.name,
                viewModel,
                selectedtask = selectedtask,
                navController,
                setBackdropState = {setBackdropState(it)},
                setSelectedTask = {setSelectedTask(it)},
                setDetailsClicked = {setDetailsClicked(it)}
            ) { setShowFAB(it) }

            RevealedRow(
                tasks,
                ActivityTypesEnum.PESTICIDE.name,
                viewModel,
                selectedtask = selectedtask,
                navController,
                setBackdropState = {setBackdropState(it)},
                setDetailsClicked = {setDetailsClicked(it)},
                setSelectedTask = {setSelectedTask(it)},
            ) { setShowFAB(it) }

            RevealedRow(
                tasks,
                ActivityTypesEnum.Other.name,
                viewModel,
                selectedtask = selectedtask ,
                navController,
                setBackdropState = {setBackdropState(it)},
                setDetailsClicked = {setDetailsClicked(it)},
                setSelectedTask = {setSelectedTask(it)},
            ) {
                setShowFAB(
                    it
                )
            }
        }
    }
}

@Composable
fun ActivityGroupSelector(
    title: String,
    activityName: String,
    selectedActivity: String,
    setSelectedActivity: (String) -> Unit
) {

    val groupSelectorWidth by animateDpAsState(
        if (selectedActivity == activityName) 130.dp else 60.dp
    )

    Box(
        modifier = Modifier
            .width(groupSelectorWidth)
            .height(60.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .background(
                if (activityName == selectedActivity) getTaskColor(activityName) else Color.White
            )
            .clickable {
                setSelectedActivity(activityName)
            }
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                getTaskIcon(activityName),
                contentDescription = null,
                modifier = Modifier.size(35.dp),
                tint = if (activityName == selectedActivity) Color.White else getTaskColor(
                    activityName
                ).copy(alpha = 0.7f)
            )

            if (selectedActivity == activityName) {
                Text(text = title, color = Color.White, style = MaterialTheme.typography.body2)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RevealedRow(
    tasks: List<Task>,
    activityName: String,
    viewModel: HomeViewModel,
    selectedtask : Task?,
    navController: NavHostController,
    setBackdropState: (BackdropValue) -> Unit,
    setDetailsClicked: (Boolean) -> Unit,
    setSelectedTask: (Task) -> Unit,
    setShowFAB: (Boolean) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Icon(
                    getTaskIcon(activityName),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = getTaskColor(activityName).copy(alpha = 0.7f)
                )
            }

            Text(
                text = viewModel.taskName(activityName),
                style = MaterialTheme.typography.body1,
                color = viewModel.taskColor(activityName),
                modifier = Modifier.padding(4.dp)
            )
        }

        LazyRow {
            items(tasks) { item ->
                if (item.activityType == activityName) {
                    TaskCard2(task = item, navController, setTaskStatus = {stat -> viewModel.setTaskStatus(item.id ,stat)}, deleteTask = {viewModel.deleteTask(it)}) {
                        setSelectedTask(item)
                        setBackdropState(BackdropValue.Revealed)
                        setDetailsClicked(true)
                    }
                }
            }
        }
    }
}
