package com.example.smartfarming.ui.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.data.network.resources.userSignupResponse.SignupResponse
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.register.*
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun Register(
    viewModel : AuthViewModel = hiltViewModel()
    ){


    val context = LocalContext.current
    val step = viewModel.step
    val firstName by viewModel.firstName.observeAsState()
    val lastName by viewModel.lastName.observeAsState()
    val email by viewModel.email.observeAsState()
    val phone by viewModel.phone.observeAsState()
    val password by viewModel.password.observeAsState()
    var repeatPassword by remember {
        mutableStateOf("")
    }

    val response by viewModel.signupResponse.observeAsState()



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (topRef, middleRef, bottomRef) = createRefs()

            RegisterTitle(
                Modifier
                    .background(MainGreen)
                    .padding(20.dp)
                    .fillMaxWidth()
                    .constrainAs(topRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )

            Body(
                modifier = Modifier
                    .constrainAs(middleRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topRef.bottom)
                        bottom.linkTo(bottomRef.top)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp),
                step = step,
                firstName = firstName!!,
                lastName = lastName!!,
                changeFirstName = {viewModel.setFirstName(it)},
                changeLastName = {viewModel.setLastName(it)},
                increaseStep = {step.value++},
                phone = phone!!,
                email = email!!,
                setPhone = {viewModel.setPhone(it)},
                setEmail = {viewModel.setEmail(it.trim())},
                password = password!!,
                setPassword = {viewModel.setPassword(it.trim())},
                repeatPassword = repeatPassword,
                setRepeatPassword = {repeatPassword = it.trim()},
                setState = {viewModel.setState(it)},
                setCity = {viewModel.setCity(it)}
            )

            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .constrainAs(bottomRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            ) {

                IconButton(
                    onClick = {
                              if (step.value > 0) step.value--
                              },
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
                    onClick = {
                        when(step.value){
                            0 -> clickHandlerStep1(
                                context,
                                name = firstName!!,
                                lastName = lastName!!,
                                step = step,
                                MAX_STEP = viewModel.MAX_STEP
                            )
                            1 -> clickHandlerStep2(
                                context = context,
                                email = email!!,
                                phone = phone!!,
                                step = step,
                                MAX_STEP = viewModel.MAX_STEP
                            )
                            2 ->  clickHandlerStep3(
                                step = step,
                                MAX_STEP = viewModel.MAX_STEP
                            )
                            3 -> clickHandlerStep4(
                                context = context,
                                password = password!!,
                                repeatPassword = repeatPassword,
                                step = step,
                                MAX_STEP = viewModel.MAX_STEP
                            )
                            else -> clickHandler(
                                context = context,
                                response = response,
                            ){
                                viewModel.signup()
                            }
                        } },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                       ,
                    shape = MaterialTheme.shapes.medium

                ) {
                    Text(
                        text = if (step.value != viewModel.MAX_STEP) "بعدی" else "تایید",
                        style = MaterialTheme.typography.h5
                    )
                }
            }

        }
    }
}

@Composable
fun Body(
    modifier: Modifier,
    step : MutableState<Int>,
    firstName : String,
    lastName : String,
    changeFirstName : (String) -> Unit,
    changeLastName : (String) -> Unit,
    increaseStep : () -> Unit,
    phone : String,
    email : String,
    setEmail : (String) -> Unit,
    setPhone : (String) -> Unit,
    password : String,
    repeatPassword : String,
    setPassword : (String) -> Unit,
    setRepeatPassword : (String) -> Unit,
    setState : (String) -> Unit,
    setCity : (String) -> Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StepCircle(step = step.value, numberTag = 0, color = MainGreen)
            StepCircle(step = step.value, numberTag = 1, color = MainGreen)
            StepCircle(step = step.value, numberTag = 2, color = MainGreen)
            StepCircle(step = step.value, numberTag = 3, color = MainGreen)
        }
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(step.value){
                0 -> Step1(
                    firstName = firstName,
                    lastName = lastName,
                    changeFirstName = changeFirstName,
                    changeLastName = changeLastName,
                    increaseStep = increaseStep
                    )
                1 -> Step2(
                    email = email,
                    phone = phone,
                    setEmail = setEmail,
                    setPhone = setPhone,
                    increaseStep = increaseStep
                )
                2 -> Step3(
                    setState = {setState(it)},
                    setCity = {setCity(it)}
                )
                3 -> Step4(
                    password = password,
                    repeatPassword = repeatPassword,
                    setPassword = setPassword,
                    setRepeatPassword = setRepeatPassword
                )

            }
        }
    }
}


fun clickHandler(
    response : Resource<SignupResponse>?,
    context: Context,
    signUp : () -> Unit
){
    signUp()
    when(response){
        is Resource.Success -> Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        is Resource.Failure -> Toast.makeText(context, "Signup failed", Toast.LENGTH_SHORT).show()
        else -> Toast.makeText(context, "Signup failed2", Toast.LENGTH_SHORT).show()
    }
}

fun increaseStep(
    step : MutableState<Int>,
    MAX_STEP : Int,
){
    if (step.value <= MAX_STEP) step.value++
}

fun clickHandlerStep1(
    context: Context,
    name : String,
    lastName: String,
    step: MutableState<Int>,
    MAX_STEP: Int
){
    if (name.length < 3 || lastName.length < 3){
        Toast.makeText(context, "نام و نام خانوادگی را به شکل صحیح وارد کنید", Toast.LENGTH_SHORT).show()
    } else {
        increaseStep(step, MAX_STEP )
    }
}

fun clickHandlerStep2(
    context: Context,
    email: String,
    phone: String,
    step: MutableState<Int>,
    MAX_STEP: Int
){
    if (!email.contains("@") || phone.length < 11){
        Toast.makeText(context, "ایمیل و شماره تلفن را به شکل صحیح وارد کنید", Toast.LENGTH_SHORT).show()
    } else {
        increaseStep(step, MAX_STEP)
    }
}

fun clickHandlerStep3(
    step: MutableState<Int>,
    MAX_STEP: Int
){
    increaseStep(step, MAX_STEP)
}

fun clickHandlerStep4(
    context: Context,
    password: String,
    repeatPassword: String,
    step: MutableState<Int>,
    MAX_STEP: Int
){
    if (password.length < 7 || password != repeatPassword){
        Toast.makeText(context, "پسورد را به شکل صحیح وارد کنید", Toast.LENGTH_SHORT).show()
    } else {
        increaseStep(step, MAX_STEP)
    }
}