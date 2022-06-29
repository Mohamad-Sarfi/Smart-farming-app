package com.example.smartfarming.ui.addactivity.activityscreens

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightGreen1
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivity.viewmodels.OthersViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.OthersViewModelFactory
import com.example.smartfarming.ui.common_composables.ProgressDots
import com.example.smartfarming.ui.common_composables.TitleIcon

@Composable
fun Others(
    gardenName: String,
    act : String,
    navController: NavHostController
){
    val activity = LocalContext.current as Activity
    val viewModel : OthersViewModel = viewModel(
        factory = OthersViewModelFactory((activity.application as FarmApplication).repo)
    )

    var step by remember {
        mutableStateOf(0)
    }
    val garden = viewModel.getGarden(gardenName).observeAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color.White,
                                Color.White,
                                LightGreen1,
                            )
                        )
                )
                .padding(25.dp)

        ) {
            val (main, button, title, progressDots) = createRefs()

            TitleIcon(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 50.dp),
                icon = painterResource(id = R.drawable.shove_line),
                LightGreen1
            )

                OthersBody(
                    modifier = Modifier
                        .constrainAs(main) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(title.bottom)
                        }
                        .height(405.dp),
                    garden = garden.value!!,
                    step
                )


            ProgressDots(
                modifier = Modifier
                    .constrainAs(progressDots) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(main.bottom)

                    }
                    .fillMaxWidth()
                    .padding(10.dp),
                step = step,
                LightGreen1
                )


            BottomRowOthers(
                modifier = Modifier
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    ,
                step = step,
                decrementStep = { step-- },
                increaseStep = {if (step < 2) step++}
                )

        }
    }
}

@Composable
fun OthersBody(
    modifier: Modifier,
    garden: Garden,
    step : Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = step == 0,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            Step1(garden = garden)
        }
        
        AnimatedVisibility(
            visible = step == 1,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            Step2(garden = garden)
        }
    }
}

@Composable
fun Step1( garden: Garden){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .graphicsLayer {
                shadowElevation = 5.dp.toPx()
                shape = RoundedCornerShape(27.dp)
                clip = true
            }
            .clip(RoundedCornerShape(27.dp))
            .background(Color.White)
            .padding(vertical = 50.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت فعالیت برای " + garden.name,
            style = MaterialTheme.typography.h3,
            color = LightGreen1,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable 
fun Step2(garden: Garden){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .graphicsLayer {
                shadowElevation = 5.dp.toPx()
                shape = RoundedCornerShape(27.dp)
                clip = true
            }
            .clip(RoundedCornerShape(27.dp))
            .background(Color.White)
            .padding(vertical = 50.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت فعالیت برای " + garden.name,
            style = MaterialTheme.typography.h3,
            color = LightGreen1,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}


@Composable
fun BottomRowOthers(
    modifier: Modifier,
    step: Int,
    decrementStep : () -> Unit,
    increaseStep : () -> Unit
){
    Row(
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                if (step > 0) decrementStep()
            },
            modifier = Modifier
                .padding(10.dp)
                .clip(MaterialTheme.shapes.large)
                .background(Color.White)
                .padding(3.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "",
                tint = LightGreen1


            )
        }

        Button(
            onClick = {
                if (step < 1) {
                    increaseStep()
                }
                else {

                }
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
            ,
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = LightGreen1
            )

        ) {
            Text(
                text = if (step != 1) "بعدی" else "تایید",
                style = MaterialTheme.typography.h5
            )
        }
    }
}