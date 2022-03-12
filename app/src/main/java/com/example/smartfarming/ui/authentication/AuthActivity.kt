package com.example.smartfarming.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.MainActivity
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModelFactory
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.LoginViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.SmartFarmingTheme

class AddUserActivity : ComponentActivity() {

    val viewModel : AuthViewModel by viewModels{
        AuthViewModelFactory(
            (application as FarmApplication).repo,
            (application as FarmApplication).authRepo
        )
    }

    val loginViewModel : LoginViewModel by viewModels{
        LoginViewModelFactory(
            (application as FarmApplication).authRepo
        )
    }


    val loggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (!loggedIn){
                        val navController = rememberNavController()
                        AuthNavGraph(navController = navController, viewModel, loginViewModel)
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
        }
    }
}


