package com.example.smartfarming.ui.authentication.register


import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Step4(
    password : String,
    repeatPassword : String,
    setPassword: (String) -> Unit,
    setRepeatPassword : (String) -> Unit
){

    val focusManager = LocalFocusManager.current
    var hiddenState by remember {
        mutableStateOf(true)
    }

    OutlinedTextField(
        value = password,
        onValueChange = {
            setPassword(it)
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
                text = "پسورد",
                style = MaterialTheme.typography.body1
            )
        },
        modifier = Modifier
            .padding(10.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType =  KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onNext = {focusManager.moveFocus(FocusDirection.Down)}
        ),
        trailingIcon = {
            Icon(
                if (hiddenState == true ) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "",
                tint = MainGreen,
                modifier = Modifier
                    .clickable {
                        hiddenState = !hiddenState
                    }
            )
        },
        visualTransformation = if (hiddenState == true) PasswordVisualTransformation() else VisualTransformation.None
    )

    OutlinedTextField(
        value = repeatPassword,
        onValueChange = {
            setRepeatPassword(it)
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
                text = "تکرار پسورد",
                style = MaterialTheme.typography.body1
            )
        },
        modifier = Modifier
            .padding(10.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()}
        ),
        trailingIcon = {
            Icon(
                if (hiddenState == true ) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "",
                tint = MainGreen,
                modifier = Modifier
                    .clickable {
                        hiddenState = !hiddenState
                    }
            )
        },
        visualTransformation = if (hiddenState == true) PasswordVisualTransformation() else VisualTransformation.None
    )

    AnimatedVisibility(
        visible = repeatPassword != password && repeatPassword.length > 0,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        Text(
            text = "پسورد صحیح نیست",
            style = MaterialTheme.typography.subtitle1,
            color = MainGreen,
            modifier = Modifier.padding(4.dp)
        )
    }
}
