package com.example.smartfarming.ui.addactivity.activityscreens.fertilization

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Compost
import androidx.compose.material.icons.outlined.Spa
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.addactivities.viewModel.FertilizationViewModel
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.ActivityTitle
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.DateSelector
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.SuccessCompose
import com.example.smartfarming.ui.addactivity.activityscreens.common_compose.WorkerNumber
import com.example.smartfarming.ui.authentication.ui.theme.sina
import com.example.smartfarming.ui.common_composables.ActivitiesStepBars
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Fertilization(gardenName : String, navController : NavHostController){
    val context = LocalContext.current
    val viewModel : FertilizationViewModel = hiltViewModel()
    val garden = viewModel.getGarden(gardenName).observeAsState()
    var gardenName = garden.value?.title ?: ""
    var startup by remember {
        mutableStateOf(false)
    }
    val userPreferences = UserPreferences.getInstance(context)

    val auth = userPreferences.authToken.collectAsState("")

    LaunchedEffect(key1 = null) {
        delay(100)
        startup = true
    }

    Scaffold(
        Modifier
            .background(Purple100)
            .fillMaxSize()
    ){
        Column(
            Modifier
                .fillMaxSize()
                .background(Purple100),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ActivityTitle(gardenName = gardenName, activityName = "تغذیه", icon = Icons.Outlined.Compost, Purple700)
            ActivitiesStepBars(viewModel.step.value, Purple700, Purple200)
            AnimatedVisibility(visible = startup) {
                FertilizationBody(viewModel, navController, auth.value!!)
            }
        }
    }
}

@Composable
fun FertilizationBody(viewModel: FertilizationViewModel, navController: NavHostController, auth: String) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 30.dp)
            .fillMaxHeight(.9f)
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
                Icons.Outlined.Compost,
                contentDescription = null,
                tint = Purple200.copy(.1f),
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
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                                    "تغذیه",
                                    date = viewModel.fertilizationDate.value,
                                    Purple700,
                                    Purple100){
                                    viewModel.fertilizationDate.value = it
                                }
                                FertilizationType(viewModel)
                                FertilizerName(viewModel)
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
                                    wNumber =viewModel.fertilizationWorker.value ,
                                    color = Purple700,
                                    colorLight = Purple100,
                                    ){
                                    viewModel.fertilizationWorker.value = it
                                }
                                FertilizationVolume(viewModel)
                            }
                        2 -> SuccessCompose(navHostController = navController)
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
                        backgroundColor = Purple700,
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
                    onClick = { viewModel.submitClickHandler(auth , context) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Purple700,
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
fun FertilizationType(viewModel: FertilizationViewModel){
    val typeList = stringArrayResource(id = R.array.fertilization_type)
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
        ) {
            Text(
                text = "نوع تغذیه",
                style = MaterialTheme.typography.subtitle1,
                color = Purple500,
            )
            Icon(Icons.Outlined.Spa, contentDescription = null, tint = Purple700, modifier = Modifier.padding(start = 10.dp))
        }

        OutlinedButton(
            onClick = { clicked = !clicked},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Purple100,
                contentColor = Purple700
            ),
            border = BorderStroke(1.dp, Purple100)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Purple700,
                    modifier = Modifier
                        .size(40.dp)
                )
                Text(
                    text = viewModel.fertilizationType.value,
                    style = MaterialTheme.typography.body1, color = Purple700, modifier = Modifier.padding(end = 20.dp))
            }
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(0.7f),
            expanded = clicked,
            onDismissRequest = { clicked = false }
        ) {
            typeList.forEach {
                DropdownMenuItem(
                    onClick = {
                        clicked = false
                        viewModel.fertilizationType.value = it
                        viewModel.setFertilizationType(it, typeList)
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
fun FertilizerName(viewModel: FertilizationViewModel){

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
                text = "نام کود",
                style = MaterialTheme.typography.subtitle1,
                color = Purple500
            )
            Icon(Icons.Outlined.Compost, contentDescription = null, tint = Purple700, modifier = Modifier.padding(start = 10.dp))
        }

        OutlinedTextField(
            value = viewModel.currentFertilizerName.value,
            onValueChange = {
                clicked = true
                viewModel.currentFertilizerName.value = it},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 0.dp)
                .background(Purple100, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Purple100,
                textColor = Purple700,
                focusedBorderColor = Purple100,
                unfocusedBorderColor = Purple100),
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
                    viewModel.addFertilizer(viewModel.currentFertilizerName.value)
                    viewModel.currentFertilizerName.value = ""
                }
            )
        )
        
        if (!viewModel.fertilizerName.isNullOrEmpty()){
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                LazyRow{
                    items(viewModel.fertilizerName.size){ index ->
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Purple500)
                                .clickable {
                                    viewModel.removeFertilizer(viewModel.fertilizerName[index])

                                }
                                .padding(vertical = 4.dp, horizontal = 9.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = viewModel.fertilizerName[index], color = Color.White, style = MaterialTheme.typography.subtitle1)
                        }
                    }
                }
            }
        }

        if (viewModel.currentFertilizerName.value != "" || clicked){
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        viewModel.addFertilizer(viewModel.currentFertilizerName.value)
                        viewModel.currentFertilizerName.value = ""
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "افزودن کود",
                    style = MaterialTheme.typography.subtitle1,
                    color = Purple500,
                )

                Icon(Icons.Default.Add, contentDescription = null, tint = Purple500, modifier = Modifier.padding(start = 4.dp))

            }

        }

    }
}

@Composable
fun FertilizationVolume(viewModel: FertilizationViewModel){

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
                color = Purple500
            )
            Icon(
                Icons.Outlined.Compost,
                contentDescription = null,
                tint = Purple700,
                modifier = Modifier.padding(start = 10.dp)
            )
        }


        OutlinedTextField(
            value = if (viewModel.fertilizationVolume.value == 0f) ""
                else viewModel.updateVolumeValueText(viewModel.fertilizationVolume.value.toString()),
            onValueChange = {viewModel.setVolumeValue(it)},
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 0.dp)
                .background(Purple100, RoundedCornerShape(20.dp))
            ,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Purple100,
                textColor = Purple700,
                focusedBorderColor = Purple100,
                unfocusedBorderColor = Purple100),
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
                    text = viewModel.decideFertilizationVolume(viewModel.fertilizationType.value, typeList),
                    style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(start = 10.dp),
                    color = Purple200
                )
            }
        )
    }
}

