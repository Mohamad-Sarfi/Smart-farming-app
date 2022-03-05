package com.example.smartfarming.ui.authentication.register

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartfarming.ui.addactivities.ui.theme.LightGray

@Composable
fun StepCircle(
    step : Int,
    numberTag: Int,
    color: Color
){
    val circle1Animation by animateDpAsState(
        targetValue = if (step == numberTag) 38.dp else 10.dp
    )

    Column(modifier = Modifier
        .wrapContentSize(Alignment.Center)
        .padding(vertical = 18.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .size(width = 10.dp, height = circle1Animation)
                .clip(RoundedCornerShape(30.dp))
                .background(color)
                .padding(14.dp)
        )
    }
}