package com.example.smartfarming.ui.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.authentication.AuthNavGraph
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModelFactory
import com.example.smartfarming.ui.authentication.ui.theme.SmartFarmingTheme
import com.example.smartfarming.ui.main_screen.bottom_navigation.NAV_PROFILE

class ProfileActivity: ComponentActivity() {
    val viewModel : ProfileViewModel by viewModels{
        ProfileViewModel.ProfileViewModelFactory(
            (application as FarmApplication).gardenRemoteRepo
        )
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

//                    ProfileCompose(viewModel = viewModel)
                }
            }
        }
    }
}