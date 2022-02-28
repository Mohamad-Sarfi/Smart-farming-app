package com.example.smartfarming.ui.gardenprofile.composables

import android.util.Log
import android.widget.Button
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.adduser.ui.theme.BlueWatering
import com.example.smartfarming.ui.adduser.ui.theme.RedFertilizer
import com.example.smartfarming.ui.adduser.ui.theme.YellowPesticide
import com.example.smartfarming.ui.gardenprofile.ScreensEnumGardenProfile

@Composable
fun ToDos(
    activityType: String,
    title : String, data: String,
    navController: NavHostController,
    gardenName: String
){

    val barColor = when(activityType){
        "irrigation" -> BlueWatering
        "fertilizarion" -> RedFertilizer
        "pesticide" -> YellowPesticide
        else -> MainGreen
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val cardHeight by animateDpAsState(
        (if (expanded) 200.dp else 90.dp),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                shape = RoundedCornerShape(15.dp)
                clip = true
            }
            .background(Color.White)
            .clip(MaterialTheme.shapes.large)
            .clickable {
                expanded = !expanded
            }
            .padding(15.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(4.dp)
            .background(barColor)
            .padding(vertical = 2.dp, horizontal = 5.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.body2, color = BorderGray)
            DetailsText(expanded, data)
            Buttons(expanded, barColor, activityType, gardenName = gardenName, navController = navController)
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailsText(expanded : Boolean, data: String){
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(
            // customize with tween AnimationSpec
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            )
        ),
        // you can also add animationSpec in fadeOut if need be.
        exit = fadeOut() + shrinkHorizontally(),

        ) {
        Text(text = data,
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.subtitle1,
            color = BorderGray
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Buttons(
    expanded : Boolean,
    color: Color,
    activityType: String,
    gardenName: String,
    navController: NavHostController
){
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = fadeOut() + shrinkVertically()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(5.dp),
                shape = MaterialTheme.shapes.large,
                border = BorderStroke(2.dp, color)
            ) {
                Text(text = "حذف", style = MaterialTheme.typography.subtitle1, color = color)
            }

            Button(
                onClick = {
                    manageButtonAction(activityType , gardenName = gardenName, navController = navController)
                },
                modifier = Modifier.padding(5.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color,
                    contentColor = Color.White
                )
            ) {
               Text(text = "انجام شد", style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}

fun manageButtonAction(activityType: String,gardenName : String, navController: NavHostController){
    when(activityType){
        ScreensEnumGardenProfile.IrrigationScreen.name ->
            navController.navigate("${ScreensEnumGardenProfile.IrrigationScreen.name}/$gardenName"){

            }
        "fertilization" ->
            navController.navigate("${ScreensEnumGardenProfile.FertilizationScreen.name}/$gardenName"){

            }
        "pesticide" ->
            navController.navigate("${ScreensEnumGardenProfile.PesticideScreen.name}/$gardenName"){

            }
        else -> navController.navigate("${ScreensEnumGardenProfile.OtherScreen.name}/$gardenName"){

        }
    }
}
