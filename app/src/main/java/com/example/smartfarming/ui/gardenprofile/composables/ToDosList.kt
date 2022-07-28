package com.example.smartfarming.ui.gardenprofile.composables

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfarming.data.room.entities.ActivityTypesEnum
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.ui.addactivities.ui.theme.BorderGray
import com.example.smartfarming.ui.addactivities.ui.theme.MainGreen
import com.example.smartfarming.ui.addactivities.ui.theme.PurpleFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.BlueWatering
import com.example.smartfarming.ui.authentication.ui.theme.PurplePrune
import com.example.smartfarming.ui.authentication.ui.theme.RedFertilizer
import com.example.smartfarming.ui.authentication.ui.theme.YellowPesticide
import com.example.smartfarming.ui.gardenprofile.ScreensEnumGardenProfile


@Composable
fun ToDos(
    task: Task,
    navController: NavHostController
){

    val barColor = when(task.activity_type){
        ActivityTypesEnum.IRRIGATION.name -> BlueWatering
        ActivityTypesEnum.FERTILIZATION.name  -> PurpleFertilizer
        ActivityTypesEnum.PESTICIDE.name  -> YellowPesticide
        ActivityTypesEnum.PRUNE.name  -> PurplePrune
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

    Card(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .fillMaxWidth()
            .height(cardHeight)
            .clickable {
                expanded = !expanded
            },
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .background(Color.White)
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
                Text(text = task.name, style = MaterialTheme.typography.body2, color = BorderGray)
                DetailsText(expanded, task.description)
                Buttons(expanded, barColor, task.activity_type, gardenName = task.garden_name, navController = navController)
            }

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
        ActivityTypesEnum.IRRIGATION.name ->
            navController.navigate("${ScreensEnumGardenProfile.IrrigationScreen.name}/$gardenName")

        ActivityTypesEnum.FERTILIZATION.name ->
            navController.navigate("${ScreensEnumGardenProfile.FertilizationScreen.name}/$gardenName")

        ActivityTypesEnum.PESTICIDE.name ->
            navController.navigate("${ScreensEnumGardenProfile.PesticideScreen.name}/$gardenName")

        else ->
            navController.navigate("${ScreensEnumGardenProfile.OtherScreen.name}/$gardenName")
    }
}
