package com.example.smartfarming.ui.authentication.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun Step1(
    changeFirstName : (String) -> Unit
){

    var firstName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = {
                firstName = it
                changeFirstName(it)
            },
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = MainGreen,
                focusedLabelColor = MainGreen,
                focusedBorderColor = MainGreen,
                unfocusedLabelColor = MainGreen
            ),
            shape = MaterialTheme.shapes.large,
            label = {
                Text(
                    text = "نام",
                    style = MaterialTheme.typography.body1
                )
            }
        )
    }
}