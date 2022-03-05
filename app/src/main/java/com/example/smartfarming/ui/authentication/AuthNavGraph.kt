package com.example.smartfarming.ui.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel

@Composable
fun AuthNavGraph(
    navController : NavHostController,
    viewModel: AuthViewModel
){
    NavHost(
        navController = navController,
        startDestination = AppScreensEnum.LoginScreen.name
    ){
        composable(
            route = "${AppScreensEnum.LoginScreen.name}"
        ){
            Login(navController = navController)
        }

        composable(
            route = "${AppScreensEnum.RegisterScreen.name}"
        ){
            Register(viewModel)
        }
    }
}