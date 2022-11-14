package com.example.smartfarming.ui.authentication

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smartfarming.ui.authentication.forgetpassword.ForgetPasswordViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgetPassWordScreen() {
    val viewModel : ForgetPasswordViewModel = hiltViewModel()
    val focusManager = LocalFocusManager.current

    Scaffold(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(visible = true) {
                OutlinedTextField(
                    value = viewModel.getPhoneNumber(),
                    onValueChange = {
                        viewModel.setPhoneNumber(it)
                    },
                    modifier = Modifier.fillMaxWidth(.8f),
                    label = {
                        Text(text = "شماره همراه خود را وارد کنید", style = MaterialTheme.typography.body2)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(

                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
            }
        }
    }
}