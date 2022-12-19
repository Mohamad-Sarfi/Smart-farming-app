package com.example.smartfarming.ui.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.MainActivity
import com.example.smartfarming.data.network.Resource
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModel
import com.example.smartfarming.ui.authentication.forgetpassword.ForgetPassWordScreen
import com.example.smartfarming.ui.authentication.register.Register

@Composable
fun AuthNavGraph(
    navController : NavHostController,
    loginViewModel: LoginViewModel
){
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val response = loginViewModel.loginResponse
    val forgotPasswordScreen = AppScreensEnum.ForgetPasswordScreen.name


    NavHost(
        navController = navController,
        startDestination = AppScreensEnum.LoginScreen.name
    ){
        composable(
            route = AppScreensEnum.LoginScreen.name
        ){
            Login(navController = navController, loginViewModel){
                // login process
                loginViewModel.login()

                if (loginViewModel.gotResponse.value){
                    when(response.value) {
                        is Resource.Success<*> -> {
                            Toast.makeText(context, "ورود موفق", Toast.LENGTH_SHORT).show()
                            activity.startActivity(Intent(context, MainActivity::class.java))
                            activity.finish()
                        }

                        is Resource.Failure -> {
                            Log.i("TAG login response", "${response.value}")
                            if ((response.value as Resource.Failure).errorCode == 406) {
                                Toast.makeText(context, "این کاربر وجود ندارد", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            Toast.makeText(
                                context,
                                "ورود ناموفق، دوباره تلاش کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            //Toast.makeText(context, "ورود ناموفق2", Toast.LENGTH_SHORT).show()
                            loginViewModel.inProgress.value = false
                            Log.i("TAG login response", "${response.value}")
                            Toast.makeText(
                                context,
                                "!ورود ناموفق، دوباره تلاش کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                            loginViewModel.login()
                        }
                    }
                }
            }
        }

        composable(
            route = AppScreensEnum.RegisterScreen.name
        ){
            Register(navHostController = navController)
        }

        composable(
            route = forgotPasswordScreen
        ){
            ForgetPassWordScreen(navController)
        }
    }
}