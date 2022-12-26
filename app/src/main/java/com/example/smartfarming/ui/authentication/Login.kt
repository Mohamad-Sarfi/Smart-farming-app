package com.example.smartfarming.ui.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.R
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModel
import com.example.smartfarming.ui.authentication.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.ui.theme.RedFertilizer
import com.example.smartfarming.utils.isOnline

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Login(
    navController: NavHostController,
    viewModel: LoginViewModel,
    signIn : () -> Unit
){
    // Username TextField
    var isUsernameEmpty by remember {
        mutableStateOf(false)
    }
    var isPassEmpty by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val focus = LocalFocusManager.current
    val snackBarState = remember {
        SnackbarHostState()
    }

    ShowAuthToasts(viewModel, context)

    if (!isOnline(context)){
        Toast.makeText(context, "ارتباط اینترنت برقرار نیست", Toast.LENGTH_SHORT).show()
        viewModel.isOffline.value = true
    }

    Scaffold(
        Modifier.fillMaxSize(),
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
        ) {
            if (viewModel.isUserPassShort.value){
                Snackbar(
                    elevation = 4.dp,
                ) {
                    Text(text = "رمز عبور و شماره تلفن اشتباه وارد شده است", style = MaterialTheme.typography.body1)
                }
            }

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
                        value = viewModel.phoneNumber.value,
                        onValueChange = {
                            viewModel.phoneNumber.value = it.trim()
                        },
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                        ,
                        label = {
                            Text(text = "شماره تلفن", style = MaterialTheme.typography.body1)
                        },
                        singleLine = true,
                        maxLines = 1,
                        textStyle = MaterialTheme.typography.body1,
                        shape = MaterialTheme.shapes.small,
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Phone,
                                contentDescription = "icon",
                                tint = if (isUsernameEmpty) MaterialTheme.colors.error else MaterialTheme.colors.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Phone
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
                        value = viewModel.password.value,
                        onValueChange = {
                            viewModel.password.value = it.trim() },
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 2.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        label = {
                            Text(text = "رمز عبور", style = MaterialTheme.typography.body1)
                                },
                        textStyle = MaterialTheme.typography.body1,
                        shape = MaterialTheme.shapes.small,
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

                val signUpWidth by animateFloatAsState(targetValue = if (viewModel.inProgress.value) 0f else 1f )
                val loginWidth by animateFloatAsState(targetValue = if (viewModel.inProgress.value) 1f else .7f )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ){
                    Button(
                        onClick = {
                            if (!viewModel.checkUserAndPassLength()){
                                //signIn()
                                viewModel.login()
                            }
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(loginWidth)
                            .padding(top = 80.dp, bottom = 20.dp),
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

                    //if (!viewModel.inProgress.value){
                        OutlinedButton(
                            onClick = {
                                navController.navigate(route = AppScreensEnum.RegisterScreen.name)
                            },
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(signUpWidth)
                                .padding(top = 80.dp, bottom = 20.dp),
                            border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "ثبت نام", style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                    // ******************************************************** Submit button
                }
            }
        }
    }
}

@Composable
private fun Title(){
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
                .padding(10.dp)
        )

        Text(
            text = "کشت افزار",
            color = Color.White,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )

        Text(
            text = "اپلیکشن مزرعه هوشمند",
            style = MaterialTheme.typography.h5,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
    }
}

@Composable
private fun submitClickHandler(context: Context, viewModel: LoginViewModel){
    if (isOnline(context)){
        if (viewModel.checkUserAndPassLength()){
            viewModel.login()
        } else {
            Toast.makeText(context, "شماره ", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "اتصال اینترنت برقرار نیست", Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun ShowAuthToasts(viewModel: LoginViewModel, context: Context) {
    when {
        viewModel.authError.value -> Toast.makeText(context, "خطای ورود، مجدد تلاش کنید", Toast.LENGTH_SHORT).show()
        viewModel.networkError.value -> Toast.makeText(context, "خطایی رخداده است، مجدد تلاش کنید", Toast.LENGTH_SHORT).show()
        viewModel.loginSuccessful.value -> Toast.makeText(context, "ورود موفق، خوش آمدید :)", Toast.LENGTH_SHORT).show()
    }

    viewModel.setAllConditionsFalse()
}
