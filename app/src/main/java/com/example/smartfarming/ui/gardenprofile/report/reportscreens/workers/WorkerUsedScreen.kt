package com.example.smartfarming.ui.gardenprofile.report.reportscreens.workers

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.addactivities.ui.theme.*
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WorkerUsedScreen(gardenName: String, navHostController: NavHostController) {
    val viewModel : WorkersUsedViewModel = hiltViewModel()
    val number = remember {
        mutableStateOf(0)
    }

    val number1 = viewModel.number
    var clicked by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = 1){
        val maxNum = number1.value
        for (i in 0..maxNum){
            delay(10)
            number.value++
        }
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { WorkerUsedTopBar(gardenName, navHostController)},
        backgroundColor = LightBackground
    ) {
        Crossfade(
            targetState = clicked,
            animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing)
        ) { click ->
            when(click){
                false -> WholeWorkerScreen(gardenName, number.value, clicked){clicked = it}
                true -> DetailedWorkerScreen()
            }
        }
    }
}

@Composable
private fun DetailedWorkerScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            DetailWorkersRow("تغذیه", 65.0, Icons.Default.Compost, Purple500)
            DetailWorkersRow("آبیاری", 23.5, Icons.Default.WaterDrop, Blue500)
            DetailWorkersRow("سمپاشی", 19.0, Icons.Default.BugReport, Yellow500)
            DetailWorkersRow("سایر", 30.5, Icons.Default.Agriculture, MainGreen)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailWorkersRow(
    title : String,
    number : Double,
    icon : ImageVector,
    color: Color
) {
    var clicked by remember {
        mutableStateOf(false)
    }
    val rowHeight by animateDpAsState(targetValue = if (clicked) 190.dp else 140.dp)
    val cornerSize by animateDpAsState(targetValue = if (clicked) 20.dp else 30.dp)

    Card(
        onClick = {},
        shape = RoundedCornerShape(cornerSize),
        //border = BorderStroke(1.dp, Gray500),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(vertical = 7.dp, horizontal = 30.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row() {
                        Text(text = title, style = MaterialTheme.typography.h6)
                        
                        Text(text = "نفر", style = MaterialTheme.typography.h6, modifier = Modifier.padding(start = 15.dp, end = 5.dp))
                        Text(text = "$number", style = MaterialTheme.typography.h6)
                    }
                    
                    Icon(icon, contentDescription = null, modifier = Modifier.size(45.dp), tint = color)
                }
//                if (clicked){
//                    Row(
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
//                        Text(text = "استفاده شده است.", style = MaterialTheme.typography.body2, modifier = Modifier.padding(horizontal = 0.dp))
//                        Text(text = title, style = MaterialTheme.typography.body2, modifier = Modifier.padding(horizontal = 0.dp))
//                        Text(text = "کارگر برای", style = MaterialTheme.typography.body2, modifier = Modifier.padding(horizontal = 2.dp))
//                        Text(text = "$number", style = MaterialTheme.typography.body2, modifier = Modifier.padding(horizontal = 5.dp))
//                    }
//                }
            }
        }
    }
}

@Composable
fun WholeWorkerScreen(gardenName: String, number : Int, clicked : Boolean, setClicked : (Boolean) -> Unit) {
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .size(270.dp)
            .clickable {
                setClicked(!clicked)
            }
            .background(MaterialTheme.colors.primary.copy(.25f))
            .padding(35.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "$number نفر ", style = MaterialTheme.typography.h2, color = Color.Black)
        }
        Row(Modifier.padding(vertical = 20.dp)) {
            Text(text = " استفاده شده است.", style = MaterialTheme.typography.body1)
            Text(text = gardenName, style = MaterialTheme.typography.body1)
            Text(text = "کارگر در باغ ", style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
private fun WorkerUsedTopBar(gardenName: String, navHostController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                navHostController.popBackStack()
            }
        ) {
            Icon(Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }
        Row(
            Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = gardenName, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onPrimary)
            Icon(Icons.Outlined.Eco, contentDescription = null, modifier = Modifier.padding(horizontal = 8.dp), tint = MaterialTheme.colors.onPrimary)
        }
    }
}