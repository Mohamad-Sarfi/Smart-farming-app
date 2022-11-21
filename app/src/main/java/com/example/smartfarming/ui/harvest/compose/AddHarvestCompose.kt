package com.example.smartfarming.ui.harvest.compose

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.network.resources.weather_response.Current
import com.example.smartfarming.data.room.entities.Harvest
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.ui.theme.*
import com.example.smartfarming.ui.common_composables.GardenSpinner
import com.example.smartfarming.ui.common_composables.PersianDatePicker
import com.example.smartfarming.ui.harvest.HarvestViewModel
import com.example.smartfarming.ui.harvest.HarvestViewModelFactory


@Composable
fun AddHarvestCompose(
    viewModel: HarvestViewModel,
    navController: NavHostController
){

    val gardenList = viewModel.getGardens().observeAsState()
    val gardenNameList = arrayListOf<String>()

    if (gardenList.value != null){
        for (g in gardenList.value!!){
            gardenNameList.add(g.title)
        }
    }

    val harvestDate = viewModel.harvestDate
    val harvestWeight = viewModel.harvestWeight.observeAsState()
    val selectedGarden by viewModel.selectedGarden.observeAsState()
    val harvestType by viewModel.harvestType.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray2),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(id = R.drawable.pistachio),
            contentDescription = null,
            tint = MainGreen,
            modifier = Modifier
                .padding(10.dp)
                .size(100.dp)
        )
            AddHarvestBody(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .border(2.dp, MainGreen, RoundedCornerShape(25.dp))
                    .background(Color.White)
                    .padding(20.dp),
                gardenNameList.ifEmpty { listOf("انتخاب باغ") },
                harvestDate = harvestDate,
                navController,
                viewModel = viewModel,
                currentGarden = selectedGarden,
                setGarden = {viewModel.selectedGarden.value = it},
                weight = harvestWeight.value,
                setWeight = {viewModel.harvestWeight.value = it},
                harvestType = harvestType,
                setHarvestType = {viewModel.harvestType.value = it}
            )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddHarvestBody(
    modifier: Modifier,
    gardenList : List<String>,
    harvestDate: MutableState<MutableMap<String, String>>,
    navController: NavHostController,
    viewModel: HarvestViewModel,
    currentGarden: String?,
    setGarden : (String) -> Unit,
    weight : Float?,
    setWeight : (Float?) -> Unit,
    harvestType : String?,
    setHarvestType: (String) -> Unit
){

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var strWeight by remember {
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
            modifier = Modifier.padding(15.dp),
            color = MainGreen
        )


        Column(Modifier.fillMaxHeight(0.7f), verticalArrangement = Arrangement.SpaceEvenly) {

            GardenSpinner(gardensList = gardenList, currentGarden = currentGarden ){
                setGarden(it)
            }

            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                HarvestTypeSpinner(harvestType ,setHarvestType = {setHarvestType(it)})

                OutlinedTextField(
                    value = strWeight,
                    onValueChange = {
                        strWeight = it
                        setWeight(strWeight.toFloatOrNull())
                    } ,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = MainGreen,
                        unfocusedBorderColor = MainGreen,
                        focusedLabelColor = MainGreen,
                        unfocusedLabelColor = MainGreen,
                        trailingIconColor = MainGreen
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    shape = MaterialTheme.shapes.medium,
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .fillMaxWidth(1f)
                        .height(60.dp),
                    keyboardActions = KeyboardActions {
                        focusManager.clearFocus()
                    },
                    textStyle = MaterialTheme.typography.body1,
                    placeholder = {
                        Text(text = "وزن", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.MonitorWeight, contentDescription = null, tint = MainGreen)
                    }
                )
            }

            HarvestDateSelector(viewModel.harvestDate.value){viewModel.harvestDate.value = it}
        }


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
                    .height(52.dp)
                    .padding(3.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }


            Button(
                onClick = {
                    if (weight == null || harvestType.isNullOrEmpty() || harvestDate.value.isEmpty()){
                        Toast.makeText(context, "تمام اطلاعات را وارد کنید", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.addHarvest2DB(
                            Harvest(
                                0,
                                weight,
                                harvestType,
                                year = harvestDate.value["year"]!!,
                                month = harvestDate.value["month"]!!,
                                day = harvestDate.value["day"]!!,
                                gardenName = currentGarden!!
                            )
                        )
                        Toast.makeText(context, "اطلاعات ثبت شد :)", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                          },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = MainGreen
                ),
                modifier = Modifier
                    .width(210.dp)
                    .height(55.dp)
                    .padding(vertical = 2.dp)
            ) {
                Text(text = "ثبت", style = MaterialTheme.typography.body2)
            }
        }
    }
}

fun submitClickHandler(insertHarvest: (Harvest) -> Unit) {
}


@Composable
fun HarvestTypeSpinner(
    harvestType: String?,
    setHarvestType : (String) -> Unit
){
    val pistachiosTypeList = stringArrayResource(id = R.array.pistachios_type)
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .height(55.dp)
            .clickable { expanded = !expanded }
        ,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = LightBackground
    ) {
        Row(modifier = Modifier
            .padding(end = 8.dp)
            .padding(end = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (harvestType.isNullOrBlank()) pistachiosTypeList[0] else harvestType,
                style = MaterialTheme.typography.body1,
                color = MainGreen
            )
        }
    }

    DropdownMenu(
        expanded = expanded, 
        onDismissRequest = {  expanded = false }) {
        pistachiosTypeList.forEach { type ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    setHarvestType(type)
                }) {
                    Text(
                        text = type,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(vertical = 5.dp, horizontal = 20.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
    }
}

@Composable
fun HarvestDateSelector(date : MutableMap<String, String> , setDate : (MutableMap<String, String>) -> Unit){

    var clicked by remember {
        mutableStateOf(false)
    }

    Card(
        Modifier
            .fillMaxWidth(0.8f)
            .height(55.dp),
        backgroundColor = LightBackground,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .clickable {
                    clicked = true
                }
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = if (date["day"] == "") "تاریخ برداشت" else "${date["year"]}/${date["month"]}/${date["day"]}", color = MainGreen, style = MaterialTheme.typography.body1)
            Icon(Icons.Default.Event, contentDescription = null, Modifier.size(25.dp), tint = MainGreen)
            if (clicked){
                PersianDatePicker(setOpenDialog = {clicked = it}, updateDate = {setDate(it)})
            }
        }
    }
}

