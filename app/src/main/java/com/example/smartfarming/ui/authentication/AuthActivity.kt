package com.example.smartfarming.ui.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.SmartFarmingTheme

class AddUserActivity : ComponentActivity() {

    val viewModel : AuthViewModel by viewModels{
        AuthViewModelFactory((application as FarmApplication).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    AuthNavGraph(navController = navController, viewModel)
                }
            }
        }
    }
}


