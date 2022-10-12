package com.example.smartfarming.ui.addactivity.activityscreens.common_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.smartfarming.R
import kotlinx.coroutines.delay

@Composable
fun SuccessCompose(navHostController : NavHostController){
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val successAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.success_animation))
        LottieAnimation(composition = successAnimation, iterations = 1)
        LaunchedEffect(key1 = null){
            delay(1700)
            navHostController.popBackStack()
        }
    }
}