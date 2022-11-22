package com.example.smartfarming.ui.addactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.AppScreensEnum
import com.example.smartfarming.ui.addactivities.SetupNavGraph
import com.example.smartfarming.ui.addactivities.ui.theme.SmartFarmingTheme
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModel
import com.example.smartfarming.ui.addactivities.viewModel.AddActivitiesViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivityActivity : ComponentActivity() {
    private val viewModel : AddActivitiesViewModel by viewModels{
        AddActivitiesViewModelFactory((application as FarmApplication).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityScreen = intent.getStringExtra("activityScreen")
        val gardenId = intent.getIntExtra("gardenId", -1)

        setContent {
            SmartFarmingTheme {
                val navController = rememberNavController()
                Log.i("TAG start screen", "$activityScreen")
                Log.i("TAG start screen", "${AppScreensEnum.PesticideScreen.name}")

                SetupNavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    startScreen = activityScreen,
                    gardenId = gardenId
                )
            }
        }
    }
}