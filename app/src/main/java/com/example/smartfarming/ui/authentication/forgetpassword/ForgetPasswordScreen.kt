package com.example.smartfarming.ui.authentication

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.forgetpassword.ForgetPasswordViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgetPassWordScreen(navController: NavHostController) {
    val viewModel : ForgetPasswordViewModel = hiltViewModel()
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    var showTextField by viewModel.showTextField

    LaunchedEffect(key1 = 1){
        delay(300)
        showTextField = true
    }

    Scaffold(
        Modifier.fillMaxSize(),
        floatingActionButton = {
            SubmitButton{
                viewModel.submitClickHandler()
            }
        },
        topBar = {
                 Row(
                     Modifier
                         .fillMaxWidth()
                         .padding(20.dp),
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.Start
                 ) {
                     Icon(
                         Icons.Outlined.ArrowBack,
                         contentDescription = null,
                         modifier = Modifier.clickable { navController.popBackStack() }.padding(10.dp),
                         tint = MaterialTheme.colors.primary
                     )
                 }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary.copy(.2f))
                    .padding(35.dp)
            ) {
                Icon(Icons.Outlined.Lock, contentDescription = null, modifier = Modifier.size(150.dp), tint = MaterialTheme.colors.primary)
            }

            AnimatedVisibility(
                visible = showTextField,
                enter = slideInVertically {
                    with(density) { -40.dp.roundToPx()}
                } + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 30.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "شماره همراه خود را وارد کنید", style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom = 10.dp))

                    OutlinedTextField(
                        value = viewModel.getPhoneNumber(),
                        onValueChange = {
                            viewModel.setPhoneNumber(it)
                        },
                        modifier = Modifier.fillMaxWidth(.75f),
                        placeholder = {
                            Text(text = "...0912", style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom = 8.dp))
                        },
                        isError = viewModel.wrongPhoneNumber.value,
                        colors = TextFieldDefaults.outlinedTextFieldColors(

                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                viewModel.evaluatePhoneNumber()
                                focusManager.clearFocus()
                            }
                        ),
                        shape = MaterialTheme.shapes.medium,
                        leadingIcon = {
                            Icon(Icons.Outlined.Phone, contentDescription = null)
                        }
                    )

                    AnimatedVisibility(
                        visible = viewModel.wrongPhoneNumber.value,
                        enter = slideInHorizontally {
                            with(density) { -40.dp.roundToPx()}
                        }
                    ) {
                        Text(
                            text = "شماره تماس اشتباه وارد شده است",
                            style = MaterialTheme.typography.subtitle2,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubmitButton(clickHandler : () -> Unit) {
    Button(onClick = {
        clickHandler()
    },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "تایید", style = MaterialTheme.typography.body2)
            Icon(Icons.Default.ArrowRight, contentDescription = null, modifier = Modifier.size(30.dp))
        }
    }
}