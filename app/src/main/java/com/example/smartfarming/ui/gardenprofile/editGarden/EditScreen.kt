package com.example.smartfarming.ui.gardenprofile.editGarden

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.common_composables.CommonTopBar

@Composable
fun EditScreen(gardenName : String, navHostController: NavHostController){

    val activity = LocalContext.current as Activity
    val viewModel : EditGardenViewModel = viewModel(factory = EditGardenViewModelFactory((activity.application as FarmApplication).repo))

    val garden = viewModel.getGarden(gardenName)

    if (garden.value != null){
        viewModel.initializeValues()
    }

    var irrigationDuration by remember {
        mutableStateOf(viewModel.irrigationDuration.value.toString())
    }

    var irrigationVolumeText by remember {
        mutableStateOf(viewModel.irrigationVolume.value.toString())
    }

    var areaText by remember {
        mutableStateOf(viewModel.area.value.toString())
    }

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = LightBackground,
        topBar = { CommonTopBar(title = "ویرایش باغ", icon = Icons.Default.Eco)},
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                IconButton(
                    onClick = { navHostController.popBackStack() },
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription =null,
                        tint = MainGreen,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(35.dp) )
                }

                Button(
                    onClick = {
                        viewModel.updateGarden()
                        navHostController.popBackStack()
                              },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MainGreen,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(0.6f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "ثبت", style = MaterialTheme.typography.body1)
                }
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (garden.value != null){
                EditTextField(
                    text = "نام باغ",
                    value = viewModel.newName.value,
                    icon = Icons.Outlined.Eco,
                    enabled = false,
                    changeValue = {viewModel.newName.value = it})
                EditTextField(
                    text = "سن باغ",
                    value =if (viewModel.newAge.value == 0) "" else viewModel.newAge.value.toString(),
                    icon = Icons.Outlined.Pin,
                    enabled = true,
                    changeValue = {viewModel.updateNewAge(it, activity)})
                Varieties(viewModel.plantVarieties, viewModel)
                EditTextField(
                    text = "ساعات آبیاری",
                    value = if (irrigationDuration == "0.0") viewModel.irrigationDuration.value.toString() else irrigationDuration,
                    icon = Icons.Outlined.Timer,
                    enabled = true,
                    changeValue = {
                        irrigationDuration = it
                        viewModel.irrigationDuration.value = it.toDoubleOrNull() ?: 0.0
                    })
                IrrigationCycleSelector("مدار آبیاری" ,viewModel)
                EditTextField(
                    text = "حجم آب",
                    value = irrigationVolumeText,
                    icon = Icons.Outlined.WaterDrop,
                    enabled = true,
                    changeValue = {
                        irrigationVolumeText = it
                        viewModel.irrigationVolume.value = it.toDoubleOrNull() ?: 0.0
                    }
                )
                LocationSelector(viewModel)
                EditTextField(text = "مساحت",
                    value = areaText ,
                    icon = Icons.Outlined.AreaChart,
                    enabled = true,
                    changeValue = {
                        areaText = it
                        viewModel.area.value = it.toDoubleOrNull() ?: 0.0
                    }
                )
                SoilSelector(viewModel)
                Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))
            }
        }
    }

}

@Composable
fun EditTextField(text : String, value : String,icon : ImageVector, enabled : Boolean , changeValue : (String) -> Unit){

    val focus = LocalFocusManager.current

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 25.dp)
            .fillMaxWidth(1f),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = value,
            onValueChange = {changeValue(it)},
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LightBackground,
                unfocusedIndicatorColor = MainGreen,
                focusedIndicatorColor = MainGreen
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(58.dp),
            keyboardActions = KeyboardActions(
                onDone = {focus.clearFocus()}
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            enabled = enabled
        )
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = text, style = MaterialTheme.typography.subtitle1, color = MainGreen, modifier = Modifier.padding(start = 1.dp))
            Icon(icon, contentDescription = null, modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp), tint = MainGreen)
        }
    }
}

@Composable
fun IrrigationCycleSelector(text: String ,viewModel: EditGardenViewModel) {
    var clicked by remember {
        mutableStateOf(false)
    }

    val itemList = stringArrayResource(id = R.array.irrigation_cycle)

    Row(
        modifier = Modifier
            .padding(vertical = 25.dp, horizontal = 25.dp)
            .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Card(
            Modifier
                .fillMaxWidth(0.55f)
                .padding(vertical = 1.dp),
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { clicked = !clicked }
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier.size(45.dp)
                )
                Text(
                    text = viewModel.irrigationCycle.value.toString() + " روز",
                    style = MaterialTheme.typography.body1,
                    color = MainGreen
                )
            }

            DropdownMenu(
                expanded = clicked,
                onDismissRequest = { clicked = !clicked }) {
                itemList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            clicked = !clicked
                            viewModel.setIrrigationCycle(itemList.indexOf(it))
                        }
                    ) {
                        Text(text = it, style = MaterialTheme.typography.body1)
                    }
                }
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                color = MainGreen,
            )
            Icon(
                imageVector = Icons.Outlined.Update,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(30.dp),
                tint = MainGreen
            )
        }
    }
}

@Composable
fun Varieties(varieties: List<String>, viewModel: EditGardenViewModel) {

    var clicked by remember {
        mutableStateOf(false)
    }
    val itemsList = stringArrayResource(id = R.array.pistachio_types)

    Column(
        Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .fillMaxWidth(0.8f)
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { clicked = !clicked }
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier.size(45.dp)
                )
                Text(
                    text = "پیوندها",
                    style = MaterialTheme.typography.body1,
                    color = MainGreen
                )
            }

        }
        DropdownMenu(
            expanded = clicked,
            onDismissRequest = { clicked = false }
        ) {
            itemsList.forEach {
                DropdownMenuItem(
                    onClick = {
                        clicked = false
                        viewModel.addVariety(it)
                    }) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }

        LazyRow {
            items(varieties) { item ->
                VarietiesItem(text = item) {
                    viewModel.removeVariety(item)
                }
            }
        }
    }
}



@Composable
fun VarietiesItem(text: String, onClickHandler : () -> Unit){
    Box(
        modifier = Modifier
            .padding(3.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MainGreen)
            .clickable { onClickHandler() }
            .padding(vertical = 5.dp, horizontal = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.subtitle1)
    }
}


@Composable
fun LocationSelector(viewModel: EditGardenViewModel){
    Row(
        modifier = Modifier
            .padding(30.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {

        Text(text = "موقعیت مکانی", style = MaterialTheme.typography.body1, color = MainGreen)
        Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = MainGreen, modifier = Modifier
            .padding(start = 10.dp)
            .size(30.dp))
    }
}

@Composable
fun SoilSelector(viewModel: EditGardenViewModel){
    var clicked by remember {
        mutableStateOf(false)
    }

    val itemList = stringArrayResource(id = R.array.soil_type)

    Row(modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 25.dp)
        .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Card(
            Modifier
                .fillMaxWidth(0.55f)
                .padding(vertical = 15.dp)
            ,
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { clicked = !clicked }
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MainGreen,
                    modifier = Modifier.size(45.dp)
                )
                Text(
                    text = viewModel.soilType.value,
                    style = MaterialTheme.typography.body1, color = MainGreen
                )
            }
            DropdownMenu(
                expanded = clicked,
                onDismissRequest = { clicked = !clicked}) {
                itemList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            clicked = !clicked
                            viewModel.soilType.value = it
                        }
                    ) {
                        Text(text = it, style = MaterialTheme.typography.body1)
                    }
                }
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "نوع خاک",
                style = MaterialTheme.typography.subtitle1,
                color = MainGreen,
            )
            Icon(
                imageVector = Icons.Outlined.Grass,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(30.dp),
                tint = MainGreen
            )
        }
    }
}









