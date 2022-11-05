package com.example.smartfarming.ui.addactivity.activityscreens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.Purple200
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModel
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModelFactory
import com.example.smartfarming.ui.common_composables.ProgressDots
import com.example.smartfarming.ui.common_composables.TitleIcon

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Fertilization(
    gardenName: String,
    navController: NavHostController
){
    val activity = LocalContext.current as Activity
    val viewModel : FertilizationViewModel =
        viewModel(factory = FertilizationViewModelFactory((activity.application as FarmApplication).repo))

    val garden = viewModel.getGarden(gardenName).observeAsState()
    var fertilizationDate = remember {
        mutableStateOf(mutableMapOf("day" to "", "month" to "", "year" to ""))
    }
    val fertilizationType by remember {
        viewModel.fertilizationType
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
                                Color.White,
                                Color.White,
                                Color.White,
                                Purple200,
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
                icon = painterResource(id = R.drawable.fertilizer_line),
                Purple200
            )
            FertilizationBody(
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
                garden = garden.value!!,
                fertilizationType = fertilizationType,
                setFertilizationType = {viewModel.setFertilizationType(it)},
                fertilizerName = "",
                setFertilizationName = {viewModel.addFertilizer(it)},
                step = step,
                increaseStep = {if (step < 2) step++},
                fertilizerVolume = viewModel.fertilizationVolume.value,
                setFertilizationVolume = {viewModel.setFertilizationVolume(it)}
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
                PurpleFertilizer
            )

            BottomRow(
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(10.dp),
                step = step,
                decreamentStep = {step--},
                increaseStep = {if (step < 2) step++}
            )

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FertilizationBody(
    modifier: Modifier,
    garden : Garden,
    fertilizationType: String,
    setFertilizationType: (String) -> Unit,
    fertilizerName : String,
    setFertilizationName: (String) -> Unit,
    fertilizerVolume : Float,
    setFertilizationVolume: (Float) -> Unit,
    step : Int,
    increaseStep : () -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت تغذیه باغ " + garden.title,
            style = MaterialTheme.typography.h3,
            color = PurpleFertilizer,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))

        // Step 0
        AnimatedVisibility(
            visible = step == 0,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FertilizationTypeSpinner(fertilizationType, setFertilizationType)
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = fertilizerName,
                    onValueChange = {setFertilizationName(it)},
                    modifier = Modifier
                        .padding(5.dp)
                        .width(220.dp)
                        .height(68.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PurpleFertilizer,
                        focusedLabelColor = PurpleFertilizer,
                        unfocusedLabelColor = PurpleFertilizer,
                        unfocusedBorderColor = PurpleFertilizer
                    ),
                    singleLine = true,
                    maxLines = 1,
                    keyboardActions = KeyboardActions(
                        onDone = {increaseStep()}
                    ),
                    label = {
                        Text(
                            text = "نام کود",
                            style = MaterialTheme.typography.subtitle1,

                        )
                    }
                )
            }
        }

        // Step 1
        val fertilizationTypeList = stringArrayResource(id = R.array.fertilization_type)
        AnimatedVisibility(
            visible = step == 1,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally()
        ) {
            OutlinedTextField(
                value = if (fertilizerVolume != 0f) "$fertilizerVolume" else "",
                onValueChange = {setFertilizationVolume(it.toFloat())},
                modifier = Modifier
                    .padding(5.dp)
                    .width(220.dp)
                    .height(68.dp),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PurpleFertilizer,
                    focusedLabelColor = PurpleFertilizer,
                    unfocusedLabelColor = PurpleFertilizer,
                    unfocusedBorderColor = PurpleFertilizer,
                    placeholderColor = BorderGray,
                    textColor = PurpleFertilizer
                ),
                singleLine = true,
                maxLines = 1,
                keyboardActions = KeyboardActions(
                    onDone = {increaseStep()}
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                placeholder = {
                    Text(
                        text =
                        when(fertilizationType){
                            fertilizationTypeList[0] -> "به متر مکعب"
                            fertilizationTypeList[1] -> "به لیتر"
                            fertilizationTypeList[1] -> "به لیتر"
                            else -> "به لیتر" },
                        style = MaterialTheme.typography.subtitle1,
                    )
                },
                label = {
                    Text(
                        text = "حجم کود",
                        style = MaterialTheme.typography.body1,
                        color = BorderGray
                    )
                }
            )
        }

    }
}




@Composable
fun FertilizationTypeSpinner(
    fertilizationType : String,
    setFertilizationType : (String) -> Unit
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
            .border(2.dp, color = PurpleFertilizer, shape = MaterialTheme.shapes.large)
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
                tint = PurpleFertilizer,
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
                        setFertilizationType(it)
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
                tint = PurpleFertilizer


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
                contentColor = PurpleFertilizer
            )

        ) {
            Text(
                text = if (step != 1) "بعدی" else "تایید",
                style = MaterialTheme.typography.h5
            )
        }
    }
}