package com.example.smartfarming.ui.addactivity.activityscreens.pesticide

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.PestControl
import androidx.compose.material.icons.outlined.Science
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.ActivityTitle
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.DateSelector
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.SuccessCompose
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.WorkerNumber
import com.example.smartfarming.ui.addactivity.viewmodels.PesticideViewModel
import com.example.smartfarming.ui.addactivity.viewmodels.PesticideViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.RedFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.ActivitiesStepBars
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Pesticide(gardenName : String, navController : NavHostController){
    val activity = LocalContext.current as Activity
    val viewModel : PesticideViewModel =
        viewModel(factory = PesticideViewModelFactory((activity.application as FarmApplication).repo))
    val garden = viewModel.getGarden(gardenName).observeAsState()
    var gardenName = garden.value?.title ?: ""
    var startup by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = null) {
        delay(100)
        startup = true
    }

    Scaffold(
        Modifier
            .background(Yellow100)
            .fillMaxSize()
    ){
        Column(
            Modifier
                .fillMaxSize()
                .background(Yellow100)
        ){
            ActivityTitle(gardenName = gardenName, activityName = "سمپاشی", icon = Icons.Outlined.PestControl, Yellow700)
            ActivitiesStepBars(viewModel.step.value, Yellow700, Yellow200)
            AnimatedVisibility(visible = startup) {
                PesticideBody(viewModel, navController, gardenName)
            }
        }
    }
}

@Composable
fun PesticideBody(viewModel: PesticideViewModel, navController: NavHostController, gardenName: String) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 30.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = 3.dp
    ) {

        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            val (bottomRow, body, backgroundPic) = createRefs()

            Icon(
                Icons.Outlined.PestControl,
                contentDescription = null,
                tint = Yellow100.copy(0.4f),
                modifier = Modifier
                    .padding(bottom = 1.dp)
                    .size(300.dp)
                    .constrainAs(backgroundPic) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .constrainAs(body) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(bottomRow.top)
                    }
            ) {
                Crossfade(
                    targetState = viewModel.step.value,
                    animationSpec = tween(500)
                ) { stepPage ->
                    when(stepPage){
                        0 ->
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                DateSelector(
                                    "سمپاشی",
                                    date = viewModel.getPesticideDate().value,
                                    Yellow700,
                                    Yellow100){
                                    viewModel.setPesticideDate(it)
                                }
                                PesticideName(viewModel)
                            }
                        1 ->
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(30.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                WorkerNumber(
                                    wNumber =viewModel.pesticideWorker.value ,
                                    color = Yellow700,
                                    colorLight = Yellow100,
                                ){
                                    viewModel.pesticideWorker.value = it
                                }
                                PesticideVolume(viewModel)
                            }
                        2 -> SuccessCompose(navController)
                    }
                }

            }

            // Button row **************************************************************************
            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomRow) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (viewModel.step.value == 0){
                            navController.popBackStack()
                        }
                        viewModel.decreaseStep()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Yellow700,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .height(55.dp)
                        .fillMaxWidth(0.2f)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }

                Button(
                    onClick = {
                              viewModel.submitClickHandler()
                         },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Yellow700,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(0.9f)
                        .height(55.dp)
                ) {
                    Text(
                        text = if (viewModel.step.value == 0) "بعدی" else "ثبت اطلاعات",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}



@Composable
fun PesticideName(viewModel: PesticideViewModel){

    val focusManager = LocalFocusManager.current
    var clicked by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "نام سم",
                style = MaterialTheme.typography.subtitle1,
                color = Yellow700
            )
            Icon(Icons.Outlined.Science, contentDescription = null, tint = Yellow700, modifier = Modifier.padding(start = 10.dp))
        }

        OutlinedTextField(
            value = viewModel.getPesticideName().value,
            onValueChange = {
                clicked = true
                viewModel.setPesticideName(it)},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 0.dp)
                .background(Yellow100, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Yellow100,
                textColor = Yellow700,
                focusedBorderColor = Yellow100,
                unfocusedBorderColor = Yellow100),
            textStyle = TextStyle(
                fontFamily = sina,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textDirection = TextDirection.ContentOrRtl,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    viewModel.addPesticide(viewModel.getPesticideName().value)
                    viewModel.setPesticideName("")
                }
            )
        )

        if (!viewModel.getPesticideList().isNullOrEmpty()){
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                LazyRow{
                    items(viewModel.getPesticideList().size){ index ->
                        var deleted by remember {
                            mutableStateOf(false)
                        }
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(if (deleted) RedFertilizer else Yellow500)
                                .clickable {
                                    deleted = true
                                    viewModel.removeFromPesticideList(viewModel.getPesticideList()[index])
                                }
                                .padding(vertical = 4.dp, horizontal = 9.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text =if (deleted) "حذف شد" else viewModel.getPesticideList()[index], color = Color.White, style = MaterialTheme.typography.subtitle1)
                        }
                    }
                }
            }
        }

        if (viewModel.getPesticideName().value != "" || clicked){
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        viewModel.addPesticide(viewModel.getPesticideName().value)
                        viewModel.setPesticideName("")

                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "افزودن کود",
                    style = MaterialTheme.typography.subtitle1,
                    color = Yellow700,
                )

                Icon(Icons.Default.Add, contentDescription = null, tint = Yellow700, modifier = Modifier.padding(start = 4.dp))

            }

        }

    }
}

@Composable
fun PesticideVolume(viewModel: PesticideViewModel){

    val focusManager = LocalFocusManager.current
    val typeList = stringArrayResource(id = R.array.fertilization_type)

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "حجم کود",
                style = MaterialTheme.typography.subtitle1,
                color = Yellow500
            )
            Icon(
                Icons.Outlined.Compost,
                contentDescription = null,
                tint = Yellow700,
                modifier = Modifier.padding(start = 10.dp)
            )
        }


        OutlinedTextField(
            value = if (viewModel.pesticideVolume.value == 0f) ""
            else viewModel.updateVolumeValueText(viewModel.pesticideVolume.value.toString()),
            onValueChange = {viewModel.setVolumeValue(it)},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 0.dp)
                .background(Purple100, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Yellow100,
                textColor = Yellow700,
                focusedBorderColor = Yellow100,
                unfocusedBorderColor = Yellow100),
            textStyle = TextStyle(
                fontFamily = sina,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                textDirection = TextDirection.ContentOrRtl,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            placeholder = {
                Text(
                    text = "لیتر",
                    style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(start = 10.dp),
                    color = Yellow200
                )
            }
        )
    }
}

