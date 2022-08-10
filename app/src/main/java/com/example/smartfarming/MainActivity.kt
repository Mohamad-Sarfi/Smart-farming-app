package com.example.smartfarming

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.smartfarming.data.room.GardenDb
import com.example.smartfarming.ui.addactivities.AddActivities
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.SmartFarmingTheme
import com.example.smartfarming.ui.addgarden.GardenNavGraph
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModel
import com.example.smartfarming.ui.authentication.authviewmodel.AuthViewModelFactory
import com.example.smartfarming.ui.main_screen.MainScreen
import com.example.smartfarming.ui.profile.ProfileViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        actionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_gardens, R.id.navigation_notifications, R.id.shopFragment
            )
        )

        navView.setupWithNavController(navController)
         */

        setContent {
            SmartFarmingTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MainGreen
                ) {
//                    val viewModel: ProfileViewModel = viewModel(
//                        factory = ProfileViewModel.ProfileViewModelFactory
//                            ((applicationContext as FarmApplication).gardenRemoteRepo)
//                    )
                    val viewModel : ProfileViewModel by viewModels{
                        ProfileViewModel.ProfileViewModelFactory(
                            (application as FarmApplication).gardenRemoteRepo
                        )
                    }
                    MainScreen(viewModel)
                }
            }
        }


    }
}