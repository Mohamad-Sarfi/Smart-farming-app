package com.example.smartfarming.ui.authentication

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.MainActivity
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun AuthNavGraph(
    navController : NavHostController,
    authviewModel: AuthViewModel,
    loginViewModel: LoginViewModel
){
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val response = loginViewModel.loginResponse.observeAsState()
    val loggedIn = false
    var loading by remember {
        mutableStateOf(false)
    }

    NavHost(
        navController = navController,
        startDestination = AppScreensEnum.LoginScreen.name
    ){
        composable(
            route = AppScreensEnum.LoginScreen.name
        ){
            Login(navController = navController, loginViewModel , loading = loading){
                // login process
                loginViewModel.login()
                loading = true
                when(response.value){
                    is Resource.Success -> {
                        loading = false
                        activity.startActivity(Intent(context, MainActivity::class.java))
                        activity.finish()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(context, (response.value as Resource.Failure).errorMessage + "   " + (response.value as Resource.Failure).errorCode.toString(), Toast.LENGTH_SHORT).show()
                        loading = false
                    }
                    else -> {
                        loading = false
                    }
   
                }
            }
        }

        composable(
            route = AppScreensEnum.RegisterScreen.name
        ){
            Register(authviewModel)
        }
    }
}

fun signIn(){

}