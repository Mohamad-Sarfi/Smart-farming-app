package com.example.smartfarming.ui.addactivities.activityscreens

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModel
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModelFactory
import com.example.smartfarming.ui.authentication.clickHandler
import com.example.smartfarming.ui.authentication.clickHandlerStep1
import com.example.smartfarming.ui.authentication.clickHandlerStep2
import com.example.smartfarming.ui.authentication.clickHandlerStep3
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.LightBlueFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.LightGreenFertilizer
import com.example.smartfarming.ui.commoncomposables.ProgressDots
import com.example.smartfarming.ui.commoncomposables.TitleIcon

@Composable
fun Fertilization(
    gardenName: String,
    navController: NavHostController
){
    val activity = LocalContext.current as Activity
    val viewModel : FertilizationViewModel =
        viewModel(factory = FertilizationViewModelFactory((activity?.application as FarmApplication).repo))

    val garden = viewModel.getGarden(gardenName).observeAsState()
    var fertilizationDate = remember {
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))
    }
    var fertilizationType by remember {
        mutableStateOf("")
    }

    var step by remember {
        mutableStateOf(0)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ){
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush
                        .verticalGradient(
                            colors = listOf(
                                MainGreen,
                                LightGreenFertilizer
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
                icon = painterResource(id = R.drawable.fertilizer_line)
            )
            Body(
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
                    .padding(vertical = 70.dp, horizontal = 10.dp),
                garden = garden.value!!,
                step = step
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
                step = step
            )

            BottomRow(
                modifier = Modifier
                    .constrainAs(button){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(10.dp),
                step = step,
                decreamentStep = {step--},
                increaseStep = {step++}
            )

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Body(
    modifier: Modifier,
    garden : Garden,
    step : Int
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت آبیاری باغ " + garden.name,
            style = MaterialTheme.typography.h3,
            color = MainGreen,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )

        AnimatedVisibility(
            visible = step == 0,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
        }

    }
}




@Composable
fun FertilizationTypeSpinner(
    fertilizationType : String,
    setFertilizarionType : (String) -> Unit
){

    var expanded by remember {
        mutableStateOf(false)
    }

    var counter by remember {
        mutableStateOf(0)
    }

    val fertilizationTypeList = stringArrayResource(id = R.array.fertilization_type)

    Box(
        modifier = Modifier
            .padding(5.dp)
            .width(220.dp)
            .height(60.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(2.dp, color = BlueWatering, shape = MaterialTheme.shapes.large)
            .padding(8.dp)
        ,
    ){
        Row(modifier =
        Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "",
                tint = LightBlueFertilizer,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(40.dp)
            )

            Row(modifier =
            Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = if (counter > 0) fertilizationType else "نوع تغذیه",
                    color = BorderGray,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(2.dp)
                )
            }

        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            fertilizationTypeList.forEach {
                DropdownMenuItem(
                    onClick = {
                        counter = 1
                        expanded = false
                        setFertilizarionType(it)
                    }
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 20.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}

@Composable
fun BottomRow(
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
                tint = MainGreen
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
                contentColor = MainGreen
            )

        ) {
            Text(
                text = if (step != 1) "بعدی" else "تایید",
                style = MaterialTheme.typography.h5
            )
        }
    }
}