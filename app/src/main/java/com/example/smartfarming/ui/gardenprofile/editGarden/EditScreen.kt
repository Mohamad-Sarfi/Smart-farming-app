package com.example.smartfarming.ui.gardenprofile.editGarden

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Eco
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.R
import com.example.smartfarming.data.room.entities.Garden
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen100
import com.example.smartfarming.ui.common_composables.CommonTopBar

@Composable
fun EditScreen(gardenName : String){

    val activity = LocalContext.current as Activity
    val viewModel : EditGardenViewModel = viewModel(factory = EditGardenViewModelFactory((activity.application as FarmApplication).repo))

    val garden = viewModel.getGarden(gardenName)

    if (garden.value != null){
        viewModel.initializeValues()
    }

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = MainGreen100,
        topBar = { CommonTopBar(title = "ویرایش باغ", icon = Icons.Default.Eco)}
    ) {
        Column(
            Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (garden.value != null){
                EditTextField(text = "نام باغ", value = viewModel.newName.value, changeValue = {viewModel.newName.value = it})
                EditTextField(text = "سن باغ", value =if (viewModel.newAge.value == 0) "" else viewModel.newAge.value.toString(), changeValue = {viewModel.updateNewAge(it, activity)})
                Varieties(viewModel.plantVarieties, viewModel)
            }
        }
    }

}

@Composable
fun EditTextField(text : String, value : String, changeValue : (String) -> Unit){

    val focus = LocalFocusManager.current

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(0.8f),
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
                backgroundColor = MainGreen100,
                unfocusedIndicatorColor = MainGreen,
                focusedIndicatorColor = MainGreen
            ),
            modifier = Modifier.fillMaxWidth(0.7f),
            keyboardActions = KeyboardActions(
                onDone = {focus.clearFocus()}
            )
        )
        Text(text = text, style = MaterialTheme.typography.body1, color = MainGreen, modifier = Modifier.padding(start = 20.dp))
    }
}

@Composable
fun Varieties(varieties : List<String>, viewModel: EditGardenViewModel){



    var clicked by remember {
        mutableStateOf(false)
    }
    val itemsList = stringArrayResource(id = R.array.pistachio_types)

    Column(
        Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .fillMaxWidth(0.8f)) {
        Card(
            Modifier
                .fillMaxWidth()
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
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = MainGreen, modifier = Modifier.size(45.dp))
                Text(text = "پیوندها", style = MaterialTheme.typography.body1, color = MainGreen)
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

        LazyRow{
            items(varieties){ item ->
                VarietiesItem(text = item){
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










