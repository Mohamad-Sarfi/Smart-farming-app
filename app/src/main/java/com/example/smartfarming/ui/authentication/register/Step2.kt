package com.example.smartfarming.ui.authentication.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@Composable
fun Step2(
    email : String,
    phone : String,
    setEmail : (String) -> Unit,
    setPhone : (String) -> Unit,
    increaseStep : () -> Unit
){
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                setEmail(it)
            },
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = MainGreen,
                focusedLabelColor = MainGreen,
                focusedBorderColor = MainGreen,
                unfocusedLabelColor = MainGreen
            ),
            shape = MaterialTheme.shapes.medium,
            label = {
                Text(
                    text = "ایمیل",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            trailingIcon = {
                Icon(Icons.Default.AlternateEmail, contentDescription = "", tint = MainGreen)
            }
        )


        ShowPhoneNumHint(phone)

    }
}

@Composable
fun ShowPhoneNumHint(phone: String){
    if (phone.length > 0){
        if (phone[0] != '0' || phone.length != 11){
            Text(
                text = "شماره تماس را بصورت ... 0912 وارد کنید",
                style = MaterialTheme.typography.subtitle1,
                color = MainGreen,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

