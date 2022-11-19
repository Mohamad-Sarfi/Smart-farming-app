package com.example.smartfarming.ui.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.MainActivity
import com.example.smartfarming.R
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModel
import com.example.smartfarming.ui.authentication.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.ui.theme.RedFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.SmartFarmingTheme
import retrofit2.Response

@Composable
fun Login(
    navController: NavHostController,
    viewModel: LoginViewModel,
    signIn : () -> Unit
){
    // Username TextField
    val usernameText by viewModel.phoneNumber.observeAsState()
    val passwordText by viewModel.password.observeAsState()

    var isUsernameEmpty by remember {
        mutableStateOf(false)
    }
    var isPassEmpty by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val focus = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Title()

        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column() {
                OutlinedTextField(
                    value = usernameText!!,
                    onValueChange = {
                        viewModel.phoneNumber.value = it.trim()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White)
                        .width(300.dp)
                        .padding(vertical = 10.dp)
                    ,
                    label = {
                        Text(text = "نام کاربری", style = MaterialTheme.typography.body1)
                    },
                    singleLine = true,
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.body1,
                    shape = MaterialTheme.shapes.medium,
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "icon",
                            tint = if (isUsernameEmpty) MaterialTheme.colors.error else MaterialTheme.colors.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focus.moveFocus(FocusDirection.Down)}
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MainGreen,
                        unfocusedLabelColor = MainGreen,
                        unfocusedBorderColor = if (isUsernameEmpty) RedFertilizer else MainGreen
                    )
                )

                // Password textField
                OutlinedTextField(
                    value = passwordText!!,
                    onValueChange = {
                        viewModel.password.value = it.trim() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White)
                        .width(300.dp)
                        .padding(vertical = 10.dp),
                    label = {
                        Text(text = "رمز عبور", style = MaterialTheme.typography.body1)
                            },
                    textStyle = MaterialTheme.typography.body1,
                    shape = MaterialTheme.shapes.medium,
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Lock,
                            contentDescription = "icon",
                            tint = if (isPassEmpty) MaterialTheme.colors.error else MaterialTheme.colors.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {focus.clearFocus()}
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MainGreen,
                        unfocusedLabelColor = MainGreen,
                        unfocusedBorderColor = if (isPassEmpty) RedFertilizer else MainGreen
                    )
                )

                Text(
                    text = "فراموشی رمز",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = AppScreensEnum.ForgetPasswordScreen.name)
                        }
                        .align(Alignment.CenterHorizontally)
                        .padding(6.dp)
                )

            }

            val loginBtnWidth by animateFloatAsState(targetValue = if (viewModel.inProgress.value) 0f else .4f )

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ){
                if (!viewModel.inProgress.value){
                    OutlinedButton(
                        onClick = {
                            navController.navigate(route = AppScreensEnum.RegisterScreen.name)
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(loginBtnWidth)
                            .padding(top = 80.dp, bottom = 20.dp)
                            .align(Alignment.CenterVertically)
                        ,
                        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = "ثبت نام", style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }

                // ******************************************************** Submit button
                Button(
                    onClick = {
                        isUsernameEmpty = usernameText!!.length < 4
                        isPassEmpty = passwordText!!.length < 6

                        if (isUsernameEmpty || isPassEmpty){
                            Toast.makeText(context, "نام کاربری و رمز عبور را بطور صحیح وارد کنید", Toast.LENGTH_SHORT).show()
                        } else{
                            signIn()
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(1f)
                        .padding(top = 80.dp, bottom = 20.dp)
                    ,
                    shape = MaterialTheme.shapes.medium,

                    ) {
                    if (!viewModel.inProgress.value){
                        Text(
                            text = "ورود",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    } else {
                        CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun Title(){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(horizontal = 35.dp, vertical = 45.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.sprout_white),
            contentDescription = "User icon",
            modifier = Modifier
                .size(120.dp)
        )

        Text(
            text = "کشت افزار",
            color = Color.White,
            style = MaterialTheme.typography.h3,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )

        Text(
            text = "اپلیکشن مزرعه هوشمند",
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
    }
}
