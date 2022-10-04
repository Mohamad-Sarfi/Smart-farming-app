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
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.MainActivity
import com.example.smartfarming.R
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.ui.addactivities.ui.theme.SmartFarmingTheme
import com.example.smartfarming.ui.authentication.AddUserActivity
import com.example.smartfarming.ui.authentication.AuthNavGraph
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

        val handler = Handler()
        handler.postDelayed({
            userPreferences.authToken.asLiveData().observe(
                this, Observer {

                    if (it == null){
                        startActivity(Intent(this, AddUserActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            )
        }, 2000)



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
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = "اپلیکیشن هوشمند مدیریت مزرعه",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(8.dp)
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