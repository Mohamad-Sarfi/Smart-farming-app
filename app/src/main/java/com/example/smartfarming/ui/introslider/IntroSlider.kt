package com.example.smartfarming.ui.introslider

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Paint.Style
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.smartfarming.R
import com.example.smartfarming.data.UserPreferences
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray2
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.authentication.AddUserActivity
import com.example.smartfarming.ui.common_composables.HorizontalStepCircle

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun IntroSlider(viewModel: IntroSliderViewModel ,setShownValue: (Boolean) -> Unit) {

    Scaffold(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LightGray2),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(text = "به کشت افزار خوش آمدید", style = MaterialTheme.typography.h4, color = MainGreen, modifier = Modifier.padding(top = 30.dp))
            }
            Crossfade(
                targetState = viewModel.step.value,
                animationSpec = tween(1000, 100,easing = FastOutLinearInEasing)
            ) { step ->
                when(step){
                    0 -> Step0()
                    1 -> Step1()
                    2 -> Step2()
                    3 -> Step3()
                }
            }
            BottomRow(viewModel){
                setShownValue(it)
            }
        }
    }
}

@Composable
private fun BottomRow(viewModel: IntroSliderViewModel, setShownValue : (Boolean) -> Unit) {
    val activity = LocalContext.current as Activity

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            HorizontalStepCircle(step = viewModel.step.value, numberTag = 0, color = MainGreen)
            HorizontalStepCircle(step = viewModel.step.value, numberTag = 1, color = MainGreen)
            HorizontalStepCircle(step = viewModel.step.value, numberTag = 2, color = MainGreen)
            HorizontalStepCircle(step = viewModel.step.value, numberTag = 3, color = MainGreen)
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    if (viewModel.step.value < 3){
                        viewModel.increaseStep()
                    } else {
                        nextBtnClickHandler(activity)
                        setShownValue(true)
                        }},
                Modifier.fillMaxWidth(.6f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(text = if (viewModel.step.value == 3) "ورود" else "بعدی", style = MaterialTheme.typography.body1)
            }
        }
    }
}

private fun nextBtnClickHandler(activity : Activity){
    activity.startActivity(Intent(activity, AddUserActivity::class.java))
}

@Composable
private fun Step0() {
    /*
    Add and manage activities
     */
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.8f)
        .padding(30.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp,
        backgroundColor = LightGray2
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                val animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.drone_irrigation))
                LottieAnimation(composition = animation, iterations = LottieConstants.IterateForever, modifier = Modifier.size(320.dp))
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(id = R.string.introStep0), style = MaterialTheme.typography.body1)
                    Text(text = stringResource(id = R.string.introStep01), style = MaterialTheme.typography.subtitle1, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun Step1() {
    /*
    add harvest info
     */
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.8f)
        .padding(25.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp,
        backgroundColor = LightGray2
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            val animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.analytics))
            LottieAnimation(composition = animation, iterations = LottieConstants.IterateForever, modifier = Modifier.size(320.dp))
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.introStep1), style = MaterialTheme.typography.body1)
                Text(text = stringResource(id = R.string.introStep11), style = MaterialTheme.typography.subtitle1, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun Step2() {
    /*
    A.I
     */
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.8f)
        .padding(25.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp,
        backgroundColor = LightGray2
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            val animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.ai))
            LottieAnimation(composition = animation, iterations = LottieConstants.IterateForever, modifier = Modifier.size(320.dp))

            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.introStep2), style = MaterialTheme.typography.body1)
                Text(text = stringResource(id = R.string.introStep22), style = MaterialTheme.typography.subtitle1, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun Step3() {
    /*
    weather
     */
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.8f)
        .padding(25.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 0.dp,
        backgroundColor = LightGray2
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            val animation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.wokers))
            LottieAnimation(composition = animation, iterations = LottieConstants.IterateForever, modifier = Modifier.size(320.dp))

            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.introStep3), style = MaterialTheme.typography.body1)
                Text(text = stringResource(id = R.string.introStep33), style = MaterialTheme.typography.subtitle1, color = Color.Gray)
            }
        }
    }
}
