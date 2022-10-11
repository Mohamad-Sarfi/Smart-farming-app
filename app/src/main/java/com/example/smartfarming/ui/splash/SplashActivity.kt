package com.example.smartfarming.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.MainActivity
import com.example.smartfarming.R
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.ui.addactivities.ui.theme.SmartFarmingTheme
import com.example.smartfarming.ui.authentication.AddUserActivity
import com.example.smartfarming.ui.authentication.AuthNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import java.util.*


class SplashActivity : ComponentActivity() {

    protected lateinit var userPreferences : UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences.getInstance(this)

        setContent {
            SmartFarmingTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Splash()
                }
            }
        }

        val context = this

        lifecycleScope.launchWhenCreated {
            delay(3000)
            userPreferences.authToken.asLiveData().observe(
                context, Observer {
                    if (it == null){
                        startActivity(Intent(context, AddUserActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(context, MainActivity::class.java))
                        finish()
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Splash(){

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 80.dp)
                    .size(200.dp)
            )
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "کشت افزار",
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(7.dp)
                    )
                    Text(
                        text = "اپلیکیشن هوشمند مدیریت مزرعه",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
        }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmartFarmingTheme {
        Splash()
    }
}