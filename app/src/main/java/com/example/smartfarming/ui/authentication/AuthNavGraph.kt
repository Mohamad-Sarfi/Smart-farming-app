package com.example.smartfarming.ui.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.MainActivity
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModel

@Composable
fun AuthNavGraph(
    navController : NavHostController,
    authviewModel: AuthViewModel,
    loginViewModel: LoginViewModel
){
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val response = loginViewModel.loginResponse.observeAsState()

    NavHost(
        navController = navController,
        startDestination = AppScreensEnum.LoginScreen.name
    ){
        composable(
            route = AppScreensEnum.LoginScreen.name
        ){
            Login(navController = navController, loginViewModel){
                loginViewModel.login()
                when(response.value){
                    is Resource.Success -> {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        activity.startActivity(Intent(context, MainActivity::class.java))
                        activity.finish()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(context, "1ورود ناموفق", Toast.LENGTH_SHORT).show()
                        Log.i("login", "${response}")
                    }
                    else -> {
                        Toast.makeText(context, "ورود ناموفق2", Toast.LENGTH_SHORT).show()
                        Log.i("login", "${response.value}")
                    }
                }

            }
        }

        composable(
            route = "${AppScreensEnum.RegisterScreen.name}"
        ){
            Register(authviewModel)
        }
    }
}

fun signIn(){

}