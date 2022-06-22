package com.example.smartfarming.ui.harvest.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.Screens.DatePicker
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.LightGreen
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.common_composables.GardenSpinner
import com.example.smartfarming.ui.harvest.HarvestViewModel


@Composable
fun AddHarvestCompose(
    viewModel: HarvestViewModel,
    navController: NavHostController
){

    val gardenList = viewModel.getGardens().observeAsState()
    val gardenNameList = arrayListOf<String>()

    if (gardenList.value != null){

        for (g in gardenList.value!!){
            gardenNameList.add(g.name)
        }
    }

    val harvestDate = viewModel.getHarvestDate()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MainGreen,
                        LightGreen,
                    )
                )
            )
    ) {
        val (pic, body) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.pistachio),
            contentDescription = "",
            modifier = Modifier
                .width(140.dp)
                .constrainAs(pic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 30.dp)
        )
            AddHarvestBody(
                modifier = Modifier
                    .constrainAs(body) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
                    .padding(20.dp),
                gardenNameList.ifEmpty { listOf("انتخاب باغ") },
                harvestDate = harvestDate,
                navController
            )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddHarvestBody(
    modifier: Modifier,
    gardenList : List<String>,
    harvestDate: MutableState<MutableMap<String, String>>,
    navController: NavHostController
){
    var currentGarden by remember {
        if (gardenList.isNotEmpty()){
            mutableStateOf(gardenList[0])
        }
        else {
            mutableStateOf("انتخاب باغ")
        }
    }

    val focusManager = LocalFocusManager.current

    var volume by remember {
        mutableStateOf("")
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ثبت محصول",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(15.dp)
        )

        GardenSpinner(gardensList = gardenList, currentGarden = currentGarden ){
            currentGarden = it
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            HarvestTypeSpinner(setHarvestType = {})

            OutlinedTextField(
                value = volume,
                onValueChange = {
                    volume = it
                } ,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = MainGreen,
                    unfocusedBorderColor = MainGreen,
                    focusedLabelColor = MainGreen,
                    unfocusedLabelColor = MainGreen,
                    trailingIconColor = MainGreen
                ),
                label = {
                    Text(text = "وزن (کیلو)", style = MaterialTheme.typography.subtitle1)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                shape = MaterialTheme.shapes.large,
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .padding(10.dp)
                    .width(180.dp)
                    .height(65.dp),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                textStyle = MaterialTheme.typography.body2
            )



        }

        DateSelectHarvest(harvestDate = harvestDate)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 50.dp)
        ) {

            IconButton(
                onClick = {navController.navigate(AppScreensEnum.HarvestHomeScreen.name){
                    popUpTo(AppScreensEnum.AddHarvestScreen.name){
                        inclusive = true
                    }
                } },
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MainGreen)
                    .height(61.dp)
                    .padding(3.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }


            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = MainGreen
                ),
                modifier = Modifier
                    .width(210.dp)
                    .height(65.dp)
                    .padding(vertical = 2.dp)
            ) {
                Text(text = "ثبت", style = MaterialTheme.typography.body2)
            }
        }
    }
}


@Composable
fun HarvestTypeSpinner(
    modifier: Modifier = Modifier,
    setHarvestType : (String) -> Unit
){
    Row(modifier = Modifier
        .padding(10.dp)
        .width(90.dp)
        .height(60.dp)
        .clip(MaterialTheme.shapes.large)
        .border(2.dp, color = MainGreen, shape = MaterialTheme.shapes.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(text = "تر", style = MaterialTheme.typography.body2)

    }
}

@Composable
fun DateSelectHarvest(
    harvestDate : MutableState<MutableMap<String, String>>,
){
    var dialogue by remember {
        mutableStateOf(false)
    }

    Button(
        onClick = { dialogue = !dialogue },
        modifier = Modifier
            .padding(20.dp)
            .width(300.dp)
            .height(60.dp)
            .clip(MaterialTheme.shapes.large)
            .border(2.dp, color = MainGreen, shape = MaterialTheme.shapes.large),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White
        )
    ) {
        Text(
            text = if (harvestDate.value["year"] == "") "تاریخ آبیاری" else "${harvestDate.value["day"]} / ${harvestDate.value["month"]} / ${harvestDate.value["year"]}",
            style = MaterialTheme.typography.body2,
            color = BorderGray,
            modifier = Modifier.padding(6.dp)
        )
    }

    if (dialogue){
        DatePicker(openDialogue = dialogue,
            changeOpenDialogue = {dialogue = !dialogue},
            updateDate = {date ->
                harvestDate.value = date
            })
    }
}