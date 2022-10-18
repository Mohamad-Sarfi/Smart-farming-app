package com.example.smartfarming.ui.introslider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.ui.authentication.ui.theme.SmartFarmingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroSliderActivity : ComponentActivity(){
    protected lateinit var userPreferences: UserPreferences
    val viewModel : IntroSliderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences.getInstance(this)

        setContent {
            SmartFarmingTheme() {
                Surface {
                    IntroSlider(viewModel){
                        viewModel.setSharedPreference(userPreferences)
                        finish()
                    }
                }
            }
        }
    }
}