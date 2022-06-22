package com.example.smartfarming.ui.common_composables

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun GardenSpinner(
    gardensList : List<String>,
    currentGarden : String,
    updateCurrentGarden : (garden : String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }

    val transition = updateTransition(targetState = transitionState, label = "transition")
    val arrowRotateDegree by transition.animateFloat({
        tween(delayMillis = 50)
    }, label = "rotationDegree") {
        if (expanded) 180f else 0f
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth(1f),
    ){
        Row(modifier = Modifier
            .width(300.dp)
            .padding(vertical = 15.dp)
            .clip(shape = MaterialTheme.shapes.large)
            .shadow(elevation = 3.dp)
            .background(Color(0xFFEEEEEE))
            .clickable {
                expanded = !expanded
            }
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(
                        end = 80.dp
                    )
                    .size(55.dp)
                    .rotate(arrowRotateDegree)
                ,

                )
            Text(
                text = currentGarden,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(1.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                gardensList.forEach { garden ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        updateCurrentGarden(garden)

                    }) {
                        Text(
                            text = garden,
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 20.dp),
                            style = MaterialTheme.typography.body2
                        )
                    }

                }
            }
        }
    }
}