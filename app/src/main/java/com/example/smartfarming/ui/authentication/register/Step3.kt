package com.example.smartfarming.ui.authentication.register

import androidx.compose.animation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.smartfarming.R
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering

@Composable
fun Step3(
    setState : (String) -> Unit,
    setCity : (String) -> Unit
){
    var city by remember {
        mutableStateOf("")
    }

    val focus = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StateSpinner{
            setState(it)
        }

        OutlinedTextField(
            value = city,
            onValueChange = {
                city = it
                setCity(it)
            },
            textStyle = MaterialTheme.typography.body1,
            label = {
                Text(
                    text = "نام شهر را وارد کنید",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .padding(10.dp),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = MainGreen,
                focusedLabelColor = MainGreen,
                focusedBorderColor = MainGreen,
                unfocusedLabelColor = MainGreen
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focus.clearFocus()
                }
            )
        )
    }




}

@Composable
fun StateSpinner(
    setState : (String) -> Unit
){

    var expanded by remember {
        mutableStateOf(false)
    }
    var state by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .padding(9.dp)
            .height(63.dp)
            .width(280.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, color = MainGreen, shape = MaterialTheme.shapes.medium)
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
            Row(modifier =
            Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = if (state == "") "استان خود را انتخاب کنید" else state,
                    color = BorderGray,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(2.dp)
                )
            }

        }
    }

    val statesList = stringArrayResource(id = R.array.states_of_Iran)

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        statesList.forEach {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    state = it
                    setState(it)
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