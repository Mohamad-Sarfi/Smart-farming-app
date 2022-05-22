package com.example.smartfarming.ui.addactivity.activityscreens

import android.app.Activity
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
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.Purple200
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.addactivity.viewmodels.PesticideViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.PesticideViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.commoncomposables.ProgressDots
import com.example.smartfarming.ui.commoncomposables.TitleIcon

@Composable
fun Pesticides(gardenName: String) {

    val activity = LocalContext.current as Activity
    val viewModel : PesticideViewModel = viewModel(
        factory = PesticideViewModelFactory((activity.application as FarmApplication).repo)
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
                                MainGreen,
                                MainGreen,
                                YellowPesticide
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
                icon = painterResource(id = R.drawable.pesticide_line)
            )

            PesticideBody(
                modifier = Modifier
                    .constrainAs(main) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(title.bottom)
                    }
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
                garden = garden.value!!
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

            )
            BottomRowPesticide(
                modifier =  Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(10.dp),
                step = step,
                decreamentStep = { step-- },
                increaseStep = {if (step < 2) step++}
            )

        }
    }
}

@Composable
fun PesticideBody(
    modifier: Modifier,
    garden : Garden
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت سم پاشی " + garden.name,
            style = MaterialTheme.typography.h3,
            color = YellowPesticide,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun BottomRowPesticide(
    modifier: Modifier,
    step: Int,
    decreamentStep : () -> Unit,
    increaseStep : () -> Unit
){
    Row(
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                if (step > 0) decreamentStep()
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
                tint = YellowPesticide


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
                contentColor = YellowPesticide
            )

        ) {
            Text(
                text = if (step != 1) "بعدی" else "تایید",
                style = MaterialTheme.typography.h5
            )
        }
    }
}