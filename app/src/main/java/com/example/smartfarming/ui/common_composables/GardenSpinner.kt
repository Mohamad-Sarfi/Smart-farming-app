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
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun GardenSpinner(
    gardensList : List<String>,
    currentGarden : String?,
    fillWidthPercentage : Float = 0.8f,
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

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(fillWidthPercentage),
        elevation = 3.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = LightGray
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
                .padding(vertical = 5.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .size(45.dp)
                    .rotate(arrowRotateDegree))
            Text(
                text = if (currentGarden.isNullOrEmpty()) "انتخاب باغ" else currentGarden,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
            )

        }
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(.7f),
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
                                .width(200.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }

                }

            }
    }
}