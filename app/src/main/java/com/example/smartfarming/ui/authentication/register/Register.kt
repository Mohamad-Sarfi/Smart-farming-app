package com.example.smartfarming.ui.authentication.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.smartfarming.MainActivity
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.userSignupResponse.SignupResponse
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Register(
    viewModel : AuthViewModel = hiltViewModel()
    ){
    val step = viewModel.step
    val activity = LocalContext.current as Activity

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RegisterTitle(
                Modifier
                    .background(MainGreen)
                    .padding(20.dp)
                    .fillMaxWidth()
            )

            SignUpBody(viewModel)

            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {

                IconButton(
                    onClick = { if (step.value > 0) step.value-- },
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MainGreen)
                        .padding(3.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                Button(
                    onClick = { clickHandler(activity ,viewModel) },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                       ,
                    shape = MaterialTheme.shapes.medium

                ) {
                    if (viewModel.waiting.value){
                        CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                    } else {
                        Text(
                            text = "ثبت نام",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }

            //if (viewModel.signupResponse.value.)
        }
    }
}

@Composable
private fun SignUpBody(viewModel: AuthViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(.7f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = viewModel.getPhoneNumber(),
            onValueChange = {
                viewModel.setPhoneNumber(it)
                viewModel.checkPhoneNumber()
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
                    text = "شماره همراه",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            trailingIcon = {
                Icon(Icons.Default.Call, contentDescription = "", tint = MainGreen)
            },

        )

        if (viewModel.phoneNumberWrong.value) {
            WrongPhoneNumber()
        }

        OutlinedTextField(
            value = viewModel.getPassword(),
            onValueChange = {
                viewModel.setPassword(it)
                viewModel.checkPassword()
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
                    text = "رمز عبور",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            trailingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "", tint = MainGreen)
            }
        )

        if (viewModel.passwordWrong.value){
            Log.i("TAG wrong pass", "wrong pass")
            WrongPassword()
        }

        OutlinedTextField(
            value = viewModel.getRepeatPassword(),
            onValueChange = {
                viewModel.setRepeatPassword(it)
                viewModel.checkRepeatPassword()
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
                    text = "تکرار رمز عبور",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 10.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "", tint = MainGreen)
            }
        )

        if (viewModel.repeatPasswordWrong.value){
            WrongRepeatPassword()
        }
    }
}

fun clickHandler(
    activity: Activity,
    viewModel: AuthViewModel,

){
    viewModel.signup()

    viewModel.signupResponse.let {
        when(viewModel.signupResponse.value){
            is Resource.Success -> {
                viewModel.waiting.value = false
                Toast.makeText(activity, "ثبت نام موفق", Toast.LENGTH_SHORT).show()
                moveToHomeScreen(activity)
            }
            is Resource.Failure -> {
                viewModel.waiting.value = false
                Toast.makeText(activity, "ثبت نام ناموفق", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun moveToHomeScreen(activity: Activity) {
    val intent = Intent(activity, MainActivity::class.java)
    activity.startActivity(intent)
    activity.finish()
}

@Composable
private fun ShowEmailHint(email: String){
    if (email.isNotEmpty()){
        if (!email.contains('@') || email.length < 4){
            Text(text = "یک ایمیل معتبر وارد کنید", style = MaterialTheme.typography.subtitle2)
        }
    }
}

@Composable
fun WrongPhoneNumber() {
    Text(text = "یک شماره همراه معتبر بصورت 0912 وارد کنید",
        style = MaterialTheme.typography.subtitle2,
        color = MaterialTheme.colors.error.copy(.7f),
        lineHeight = 23.sp)
}

@Composable
fun WrongPassword() {
    Row(
        Modifier
            .fillMaxWidth(.8f)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "طول رمز عبور وارد شده باید حداقل 8 و شامل عدد و حروف انگلیسی باشد",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.error.copy(.7f),
            lineHeight = 23.sp
        )
    }
}

@Composable
fun WrongRepeatPassword() {
    Row(
        Modifier
            .fillMaxWidth(.8f)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "تکرار رمز عبور صحیح نیست", style = MaterialTheme.typography.subtitle2, color = MaterialTheme.colors.error.copy(.7f))
    }
}